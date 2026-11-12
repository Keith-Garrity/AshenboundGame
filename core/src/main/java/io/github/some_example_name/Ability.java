package io.github.some_example_name;

/** One thing the player can do on their turn. Damage, block, and heal can be mixed. */
public class Ability {
    private final String name;
    private final String description;
    private final int damage;
    private final int block;
    private final int heal;
    private final int cooldown;   // turns it can't be used after using it (0 = every turn)

    public Ability(String name, String description, int damage, int block, int heal, int cooldown) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.block = block;
        this.heal = heal;
        this.cooldown = cooldown;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getDamage() { return damage; }
    public int getBlock() { return block; }
    public int getHeal() { return heal; }
    public int getCooldown() { return cooldown; }
}
