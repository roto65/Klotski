package core;

import java.awt.*;

/**
 * Provides objects that describe the four cardinal directions
 */
public enum Direction {

    /**
     * Represents the north direction
     */
    UP (new Point(0, -1)),

    /**
     * Represents the south direction
     */
    DOWN (new Point(0, 1)),

    /**
     * Represents the east direction
     */
    RIGHT (new Point(1, 0)),

    /**
     * Represents the west direction
     */
    LEFT (new Point(-1, 0));

    /**
     * This point represents the tip of the vector with given direction starting form the origin and with module 1
     */
    private final Point vector;

    /**
     * Constructor method used to create the enum's values
     *
     * @param vector the associated value
     */
    Direction(Point vector) {
        this.vector = vector;
    }

    /**
     * @return the vector associated with each value
     */
    public Point getVector() {
        return vector;
    }
}
