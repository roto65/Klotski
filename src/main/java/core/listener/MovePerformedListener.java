package core.listener;

import java.util.EventListener;

/**
 * Implementing this interface allows an object to get information about things that need to happen as a result of a
 * block being moved
 */
public interface MovePerformedListener extends EventListener {

    /**
     * This method adds 1 to the current move counter
     */
    void incrementMoveCounter();

    /**
     * This method tells the dashboard object to open a new post game dialog
     */
    void triggerPostGame();
}
