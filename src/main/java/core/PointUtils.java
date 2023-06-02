package core;

import java.awt.*;

public class PointUtils {

        public static Point add(Point p1, Point p2) {
            return new Point(p1.x + p2.x, p1.y + p2.y);
        }

        public static Point subtract(Point p1, Point p2) {
            return new Point(p1.x - p2.x, p1.y - p2.y);
        }
}
