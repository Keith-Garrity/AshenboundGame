package io.github.some_example_name;

public class Zombie extends Enemy {
    public Zombie() {
        super("Zombie", 80, 8, 4,
            "A slow, tough corpse. It only attacks every other turn.");
    }
    @Override
    public String getMoveName(int turn) {
        return (turn % 2 == 0) ? "Claw" : "Shamble";
    }
    @Override
    public int getMoveDamage(int turn) {
        return (turn % 2 == 0) ? getAttack() * 2 : 0;    // even turns: heavy hit, odd turns: does nothing
    }
}
