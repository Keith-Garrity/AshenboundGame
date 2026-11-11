package io.github.some_example_name;

/** Base class for every enemy. */
public abstract class Enemy {
    private final String name;
    private final int maxHealth;
    private int health;
    private final int attack;      // base damage; moves use this number
    private final int defense;
    private final String description;

    protected Enemy(String name, int maxHealth, int attack, int defense, String description) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.description = description;
    }

    /** What the enemy does on a given turn (turn 1 is the first turn of the fight). */
    public abstract String getMoveName(int turn);

    /** How much damage that move deals (0 means it doesn't attack that turn). */
    public abstract int getMoveDamage(int turn);

    public void takeDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    public boolean isAlive() {
        return health > 0;
    }

    /** File to load for this enemy's picture, e.g. wolf.png */
    public String getImagePath() {
        return "enemies/" + name.toLowerCase().replace(" ", "_") + ".png";
    }

    public String getName() { return name; }
    public int getMaxHealth() { return maxHealth; }
    public int getHealth() { return health; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public String getDescription() { return description; }
}
