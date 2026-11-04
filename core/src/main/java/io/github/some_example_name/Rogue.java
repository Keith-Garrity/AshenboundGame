package io.github.some_example_name;

public class Rogue extends CharacterClass{
    public Rogue() {
        super(0, "Rogue", 75, 3, 6, "A quick fighter who plays many cheap cards..");
    }

    @Override
    public String getSpecialAbility() {
        return "Quick Hands: Draw an extra card each turn." +
            "Evasion: 20% Chance to dodge an attack.";
    }
}
