package core;

public class Move {

    private int steps;
    private Direction direction;

    public Move(int steps, Direction direction) {
        this.steps = steps;
        this.direction = direction;
    }

    public int getSteps() {
        return steps;
    }

    public Direction getDirection() {
        return direction;
    }
}
