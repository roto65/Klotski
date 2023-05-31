package core;

import java.awt.*;

public class Move {

    private Point startPos;
    private Point endPos;

    public Move() {
    }

    public Move(Point startPos, Point endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public Point getStartPos() {
        return startPos;
    }

    public Point getEndPos() {
        return endPos;
    }

    public Move reverse() {

        Move reversedMove = new Move();

        reversedMove.endPos = startPos;
        reversedMove.startPos = endPos;

        return reversedMove;
    }

    public int evalSteps() {

        int deltaX = Math.abs(endPos.x - startPos.x);
        int deltaY = Math.abs(endPos.y - startPos.y);

        if (deltaX != 0 && deltaY != 0)
            throw new RuntimeException();

        return Math.max(deltaX, deltaY);
    }

    public Direction evalDirection() {

        int deltaX = endPos.x - startPos.x;
        int deltaY = endPos.y - startPos.y;

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            if (deltaX >= 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            if (deltaY >= 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
    }

    @Override
    public String toString() {
        return "Move{" +
                "startPos=" + startPos +
                ", endPos=" + endPos +
                '}';
    }
}