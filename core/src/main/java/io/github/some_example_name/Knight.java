package io.github.some_example_name;

public class Knight extends CharacterClass{
    public Knight() {
        super(0, "Knight", 100, 3, 5, "A sturdy warrior who wins by outlasting enemies.");
    }

    @Override
    public String getSpecialAbility() {
        return "Bulwark: gains extra Block from defense cards." +
            "Mullgian: Survives one fatal blow and recovers to 30% HP.";
    }
}
