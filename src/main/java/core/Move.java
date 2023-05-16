package core;

import java.awt.*;

public class Move {

    private Point startPos;
    private int steps;
    private Direction direction;

    public Move() {
    }

    public Move(Point startPos, int steps, Direction direction) {
        this.startPos = startPos;
        this.steps = steps;
        this.direction = direction;
    }

    public Point getStartPos() {
        return startPos;
    }

    public int getSteps() {
        return steps;
    }

    public Direction getDirection() {
        return direction;
    }

    public Move reverse() {

        Move reversedMove = new Move();
        reversedMove.steps = steps;

        switch (direction) {
            case UP -> {
                reversedMove.direction = Direction.DOWN;
                reversedMove.startPos = new Point(startPos.x, startPos.y - steps);
            }
            case DOWN -> {
                reversedMove.direction = Direction.UP;
                reversedMove.startPos = new Point(startPos.x, startPos.y + steps);
            }
            case LEFT -> {
                reversedMove.direction = Direction.RIGHT;
                reversedMove.startPos = new Point(startPos.x - steps, startPos.y);
            }
            case RIGHT -> {
                reversedMove.direction = Direction.LEFT;
                reversedMove.startPos = new Point(startPos.x + steps, startPos.y);
            }
        }

        return reversedMove;
    }
}
