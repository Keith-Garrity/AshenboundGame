package io.github.some_example_name;

/** A player's character: a name, a class (Knight/Mage/Rogue), a gender, a level, and health. */
public class Character {
    private final String name;
    private final CharacterClass characterClass;
    private final Gender gender;
    private int level;
    private int maxHealth;
    private int health;
    public int statPoints = 0;

    /** Make a character with a name you choose. */
    public Character(String name, CharacterClass characterClass, Gender gender) {
        this.name = name;
        this.characterClass = characterClass;
        this.gender = gender;
        this.level = 1;
        this.maxHealth = characterClass.getMaxHealth();
        this.health = maxHealth;
    }

    /** Make a character with a random name that fits the gender. */
    public Character(CharacterClass characterClass, Gender gender) {
        this(NameGenerator.randomName(gender), characterClass, gender);
    }

    /** File to load for this character's picture, e.g. "characters/knight_male.png". */
    public String getImagePath() {
        return "characters/" + characterClass.getName().toLowerCase()
            + "_" + gender.name().toLowerCase() + ".png";
    }

    public void takeDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void levelUp() {
        level++;
        maxHealth += 5;
        health = maxHealth;
        statPoints += 3;
    }

    public String getName() { return name; }
    public CharacterClass getCharacterClass() { return characterClass; }
    public Gender getGender() { return gender; }
    public int getLevel() { return level; }
    public int getMaxHealth() { return maxHealth; }
    public int getHealth() { return health; }
}
