package io.github.some_example_name;

public class Wolf extends Enemy{
    public Wolf() {
        super("Wolf", 50, 12, 0,
            "A hungry wolf. Weak, but its bite hits hard.");
    }
    @Override
    public String getMoveName(int turn) {
        return (turn % 3 == 0) ? "Claw" : "Bite" ;
    }
    @Override
    public int getMoveDamage(int turn) {
        return (turn % 3 == 0) ? getAttack() * 2 : getAttack();   // every 3rd turn: double damage
    }
}
