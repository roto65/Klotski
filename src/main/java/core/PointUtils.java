package core;

import java.awt.*;

import static main.Constants.COLUMNS;

public class PointUtils {

    public static Point add(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    public static Point subtract(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }

    public static Point multiply(Point p, int i) {
        return new Point(p.x * i, p.y * i);
    }

    public static int toIndex(Point p) {
        return p.y * COLUMNS + p.x;
    }
}
