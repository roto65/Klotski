package core;

import java.awt.*;

import static main.Constants.COLUMNS;

/**
 * Utilities class for point operations. The points are treated as vectors
 */
public class PointUtils {

    /**
     * This method adds two points
     *
     * @param p1 first point to add
     * @param p2 second point to add
     * @return the result of the operation
     */
    public static Point add(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    /**
     * This method subtracts two points
     *
     * @param p1 first point to subtract
     * @param p2 secondo point to subtract
     * @return the result of the operation
     */
    public static Point subtract(Point p1, Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }

    /**
     * This method multiplies a point by a scalar
     *
     * @param p the point to multiply
     * @param i the scalar to multiply
     * @return the result of the operation
     */
    public static Point multiply(Point p, int i) {
        return new Point(p.x * i, p.y * i);
    }

    /**
     * This method converts a point to the index of the cell it points as if the board is a one-dimensional array
     *
     * @param p the point to convert
     * @return the resulting index
     */
    public static int toIndex(Point p) {
        return p.y * COLUMNS + p.x;
    }
}
