package io.github.some_example_name;

public class Bandit extends Enemy {
    public Bandit() {
        super("Bandit", 90, 9, 2,
            "A low-level thug with a rusty blade.");
    }
    public String getMoveName(int turn) {
        return (turn % 3 == 0) ? "Cheap Shot" : "Slash";
    }
    public int getMoveDamage(int turn) {
        return (turn % 3 == 0) ? getAttack() + 4 : getAttack();   // every 3rd turn: +4 damage
    }
}
