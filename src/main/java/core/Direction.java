package core;

import java.awt.*;

public enum Direction {
    UP (new Point(0, -1)),
    DOWN (new Point(0, 1)),
    RIGHT (new Point(1, 0)),
    LEFT (new Point(-1, 0));

    private final Point vector;

    Direction(Point vector) {
        this.vector = vector;
    }

    public Point getVector() {
        return vector;
    }
}
