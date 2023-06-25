package core;

import java.awt.*;

import static main.Constants.COLUMNS;

/**
 * Defines object that describe a movement of a block
 */
public class Move {

    /**
     * Point that represents the start coordinates of the move
     */
    private Point startPos;

    /**
     * Point that represents the end coordinates of the move
     */
    private Point endPos;

    /**
     * Constructor that instantiates an empty move
     */
    public Move() {
    }

    /**
     * Constructor that assigns the parameter values to the new move
     *
     * @param startPos starting position
     * @param endPos ending position
     */
    public Move(Point startPos, Point endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    /**
     * Constructor that creates a new move based on the index of the board cells
     *
     * @param startIndex index of the start cell
     * @param endIndex index of the end cell
     * @see #fromIndex(int)
     */
    public Move(int startIndex, int endIndex) {
        this.startPos = fromIndex(startIndex);
        this.endPos = fromIndex(endIndex);
    }

    /**
     * @return the start position coordinates as a Point
     */
    public Point getStartPos() {
        return startPos;
    }

    /**
     * @return the end position coordinates as a Point
     */
    public Point getEndPos() {
        return endPos;
    }

    /**
     * @param startPos the coordinates of the new start position
     */
    public void setStartPos(Point startPos) {
        this.startPos = startPos;
    }

    /**
     * @param endPos the coordinates of the new end position
     */
    public void setEndPos(Point endPos) {
        this.endPos = endPos;
    }

    /**
     * This method reverses the move.
     * The result is very similar to when you multiply a vector by -1
     *
     * @return the newly reversed move
     */
    public Move reverse() {

        Move reversedMove = new Move();

        reversedMove.endPos = startPos;
        reversedMove.startPos = endPos;

        return reversedMove;
    }

    /**
     * This method calculates the distance a block travels when this move is applied to it
     *
     * @return the number of cells traveled
     */
    public int evalSteps() {

        int deltaX = Math.abs(endPos.x - startPos.x);
        int deltaY = Math.abs(endPos.y - startPos.y);

        if (deltaX != 0 && deltaY != 0) {

            System.out.println("Start: " + startPos + ", End: " + endPos);

            //throw new RuntimeException();
        }

        return Math.max(deltaX, deltaY);
    }

    /**
     * This method calculates the direction a block travels when this move is applied to it
     *
     * @return the direction the block moved
     */
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

    /**
     * This method finds if a move cuts a corner or not
     *
     * @return true if this move cuts a corner
     */
    public boolean isCut() {
        int deltaX = Math.abs(endPos.x - startPos.x);
        int deltaY = Math.abs(endPos.y - startPos.y);

        return deltaX != 0 && deltaY != 0;
    }

    /**
     * This method calculates the first half of a cut move
     *
     * @param state the current board disposition as a String
     * @return the first half of this move
     */
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

    /**
     * This method calculates the index of a point if the game board is considered like an array
     *
     * @param index the starting index
     * @return new evaluated point
     */
    private Point fromIndex(int index) {
        return new Point(index % COLUMNS, index / COLUMNS);
    }
}
