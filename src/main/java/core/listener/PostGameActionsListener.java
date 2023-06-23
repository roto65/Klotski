package core.listener;

import java.util.EventListener;

public interface PostGameActionsListener extends EventListener {
    void reset();
    boolean loadLevel();
    boolean loadLevelFromFile();
    void exit();
}
