package io.github.some_example_name;
import java.util.Arrays;
import java.util.List;

public class Knight extends CharacterClass{
    public Knight() {
        super(0, "Knight", 100, 3, 5, "A sturdy warrior who wins by outlasting enemies.");
    }

    @Override
    public String getSpecialAbility() {
        return "Bulwark: gains extra Block from defense cards." +
            "Mullgian: Survives one fatal blow and recovers to 30% HP.";
    }
    public List<Ability> getAbilities() {
        return Arrays.asList(
            //          name           description                          dmg block heal cooldown
            new Ability("Slash",       "Deal 7 damage.",                      7, 0, 0, 0),
            new Ability("Shield Bash", "Deal 4 damage and gain 4 Block.",     4, 4, 0, 1),
            new Ability("Guard",       "Gain 8 Block.",                       0, 8, 0, 0),
            new Ability("Rally",       "Heal 8 HP.",                          0, 0, 8, 3)
        );
    }
}
