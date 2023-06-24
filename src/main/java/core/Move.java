package core;

import java.awt.*;

import static main.Constants.COLUMNS;

public class Move {

    private Point startPos;
    private Point endPos;

    public Move() {
    }

    public Move(Point startPos, Point endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public Move(int startIndex, int endIndex) {
        this.startPos = fromIndex(startIndex);
        this.endPos = fromIndex(endIndex);
    }

    public Point getStartPos() {
        return startPos;
    }

    public Point getEndPos() {
        return endPos;
    }

    public void setStartPos(Point startPos) {
        this.startPos = startPos;
    }

    public void setEndPos(Point endPos) {
        this.endPos = endPos;
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

        if (deltaX != 0 && deltaY != 0) {

            System.out.println("Start: " + startPos + ", End: " + endPos);

            //throw new RuntimeException();
        }

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

    public boolean isCut() {
        int deltaX = Math.abs(endPos.x - startPos.x);
        int deltaY = Math.abs(endPos.y - startPos.y);

        return deltaX != 0 && deltaY != 0;
    }

    public Move evalCutMove(String state) {
        int deltaX = endPos.x - startPos.x;
        int deltaY = endPos.y - startPos.y;

        Point halfX = PointUtils.add(startPos, PointUtils.multiply(Direction.RIGHT.getVector(), deltaX));
        Point halfY = PointUtils.add(startPos, PointUtils.multiply(Direction.DOWN.getVector() , deltaY));

        if (state.charAt(PointUtils.toIndex(halfX)) == '0') {
            return new Move(startPos, halfX);
        } else {
            return new Move(startPos, halfY);
        }
    }

    private Point fromIndex(int index) {
        return new Point(index % COLUMNS, index / COLUMNS);
    }
}
