package io.github.some_example_name;
import java.util.Arrays;
import java.util.List;

public class Mage extends CharacterClass{
    public Mage() {
        super(0, "Mage", 60, 4, 5, "A fragile spellcaster who is attuned with elemental energy.");
    }

    @Override
    public String getSpecialAbility() {
        return "Mana Reserve: Unspent energy carries to your next turn (Max 2)." +
            "Energy Attuned: Start each turn with an extra energy.";
    }
    @Override
    public List<Ability> getAbilities() {
        return Arrays.asList(
            new Ability("Firebolt",     "Deal 8 damage.",                    8, 0, 0, 0),
            new Ability("Arcane Blast", "Deal 14 damage.",                  14, 0, 0, 3),
            new Ability("Barrier",      "Gain 7 Block.",                     0, 7, 0, 0),
            new Ability("Drain",        "Deal 5 damage and heal 5 HP.",      5, 0, 5, 2)
        );
    }
}
