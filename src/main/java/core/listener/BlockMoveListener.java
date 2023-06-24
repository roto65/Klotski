package core.listener;

import java.awt.*;
import java.util.EventListener;

/**
 * Implementing this interface allows an object to get information about all the blocks that ar moved on the game board
 */
public interface BlockMoveListener extends EventListener {

    /**
     * This method defines behaviour when the <i>block moved</i> event is performed
     *
     * @param start The coordinate where the actions started
     * @param end The coordinate where the action ended
     */
    void blockMoved(Point start, Point end);
}
