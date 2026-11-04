package io.github.some_example_name;

public class Mage extends CharacterClass{
    public Mage() {
        super(0, "Knight", 60, 4, 5, "A fragile spellcaster who is attuned with elemental energy.");
    }

    @Override
    public String getSpecialAbility() {
        return "Mana Reserve: Unspent energy carries to your next turn (Max 2)." +
            "Energy Attuned: Start each turn with an extra energy.";
    }
}
