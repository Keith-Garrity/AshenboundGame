package io.github.some_example_name;
import java.util.Arrays;
import java.util.List;

public class Rogue extends CharacterClass{
    public Rogue() {
        super(0, "Rogue", 75, 3, 6, "A quick fighter who plays many cheap cards..");
    }

    @Override
    public String getSpecialAbility() {
        return "Quick Hands: Draw an extra card each turn." +
            "Evasion: 20% Chance to dodge an attack.";
    }
    @Override
    public List<Ability> getAbilities() {
        return Arrays.asList(
            new Ability("Stab",     "Deal 6 damage.",                   6, 0, 0, 0),
            new Ability("Backstab", "Deal 12 damage.",                 12, 0, 0, 2),
            new Ability("Dodge",    "Gain 7 Block.",                    0, 7, 0, 0),
            new Ability("Bandage",  "Heal 6 HP.",                       0, 0, 6, 3)
        );
    }
}
