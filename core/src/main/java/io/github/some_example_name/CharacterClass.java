package io.github.some_example_name;
import java.util.List;
/** Base class for the playable classes.
 * Each class (Knight, Mage, Rogue) fills in its own stats. */
public abstract class CharacterClass {
    private final int id;              // matches the ClassType column in the Character table
    private final String name;
    private final int maxHealth;
    private final int energyPerTurn;   // energy to play cards each turn
    private final int cardsPerTurn;    // cards drawn each turn
    private final String description;
    protected CharacterClass(int id, String name, int maxHealth,
                             int energyPerTurn, int cardsPerTurn, String description) {
        this.id = id;
        this.name = name;
        this.maxHealth = maxHealth;
        this.energyPerTurn = energyPerTurn;
        this.cardsPerTurn = cardsPerTurn;
        this.description = description;
    }
    /** Each class describes its own special ability. */
    public abstract String getSpecialAbility();
    /** The four abilities this class can choose from in combat. */
    public abstract List<Ability> getAbilities();

    /** Turns a ClassType number from the database into the right class. */
    public static CharacterClass fromId(int id) {
        switch (id) {
            case 0:
                return new Knight();
            case 1:
                return new Mage();
            case 2:
                return new Rogue();
            default:
                throw new IllegalArgumentException("Unknown class id: " + id);

        }
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getMaxHealth() { return maxHealth; }
    public int getEnergyPerTurn() { return energyPerTurn; }
    public int getCardsPerTurn() { return cardsPerTurn; }
    public String getDescription() { return description; }
}
