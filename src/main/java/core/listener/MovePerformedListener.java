package core.listener;

import java.util.EventListener;

public interface MovePerformedListener extends EventListener {
    void incrementMoveCounter();
    void triggerPostGame();
}
