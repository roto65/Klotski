package core;

import java.awt.*;
import java.util.EventListener;

public interface BlockMoveListener extends EventListener {
    void blockMoved(Point start, Point end);
}
