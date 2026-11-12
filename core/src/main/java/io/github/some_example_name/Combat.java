package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

/** One fight between the player and one enemy. The player picks an ability, then the enemy acts. */
public class Combat {
    private final Character player;
    private final Enemy enemy;
    private final List<Ability> abilities;
    private final int[] cooldownLeft;                 // turns left before each ability can be used again
    private final List<String> log = new ArrayList<>();
    private int turn = 1;
    private int playerBlock = 0;

    public Combat(Character player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.abilities = player.getCharacterClass().getAbilities();
        this.cooldownLeft = new int[abilities.size()];
        addLog("A " + enemy.getName() + " appears!");
    }

    // ---------- the player's turn ----------

    public boolean canUse(int index) {
        return !isOver() && index >= 0 && index < abilities.size() && cooldownLeft[index] == 0;
    }

    public void useAbility(int index) {
        if (!canUse(index)) return;
        Ability a = abilities.get(index);
        List<String> parts = new ArrayList<>();

        if (a.getDamage() > 0) {
            // a little stronger at higher levels; the enemy's defense reduces it (always at least 1)
            int damage = Math.max(1, a.getDamage() + player.getLevel() - 1 - enemy.getDefense());
            enemy.takeDamage(damage);
            parts.add("deals " + damage + " damage");
        }
        if (a.getBlock() > 0) {
            playerBlock += a.getBlock();
            parts.add("gains " + a.getBlock() + " Block");
        }
        if (a.getHeal() > 0) {
            int before = player.getHealth();
            player.heal(a.getHeal());
            parts.add("heals " + (player.getHealth() - before));
        }
        addLog(player.getName() + " uses " + a.getName() + ": " + String.join(", ", parts) + ".");

        cooldownLeft[index] = a.getCooldown() > 0 ? a.getCooldown() + 1 : 0;
        endPlayerTurn();
    }

    private void endPlayerTurn() {
        if (!enemy.isAlive()) {
            addLog(enemy.getName() + " is defeated!");
            return;                                   // fight over: the enemy doesn't get a turn
        }
        enemyTurn();
    }

    // ---------- the enemy's turn ----------

    private void enemyTurn() {
        String move = enemy.getMoveName(turn);
        int damage = enemy.getMoveDamage(turn);

        if (damage == 0) {
            addLog(enemy.getName() + " uses " + move + ".");
        } else {
            int blocked = Math.min(playerBlock, damage);      // Block soaks up damage first
            playerBlock -= blocked;
            player.takeDamage(damage - blocked);
            addLog(enemy.getName() + " uses " + move + " for " + damage
                + (blocked > 0 ? " (" + blocked + " blocked)." : "."));
        }

        if (!player.isAlive()) {
            addLog(player.getName() + " is defeated!");
        }

        playerBlock = 0;                              // Block wears off when the new round starts
        for (int i = 0; i < cooldownLeft.length; i++) {
            if (cooldownLeft[i] > 0) cooldownLeft[i]--;
        }
        turn++;
    }

    // ---------- information for the screen ----------

    /** What the enemy will do next */
    public String getEnemyIntent() {
        int damage = enemy.getMoveDamage(turn);
        return enemy.getMoveName(turn) + (damage > 0 ? " (" + damage + ")" : " (no attack)");
    }

    public boolean isOver() { return !enemy.isAlive() || !player.isAlive(); }
    public boolean playerWon() { return !enemy.isAlive(); }

    public List<Ability> getAbilities() { return abilities; }
    public int getCooldownLeft(int index) { return cooldownLeft[index]; }
    public Character getPlayer() { return player; }
    public Enemy getEnemy() { return enemy; }
    public int getTurn() { return turn; }
    public int getPlayerBlock() { return playerBlock; }

    /** The last three log lines. */
    public List<String> getRecentLog() {
        int from = Math.max(0, log.size() - 3);
        return log.subList(from, log.size());
    }

    private void addLog(String line) {
        log.add(line);
    }
}
