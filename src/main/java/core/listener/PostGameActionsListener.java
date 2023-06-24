package core.listener;

import java.util.EventListener;


/**
 * Implementing this interface allows a Dashboard object to execute commands when a button press happens
 */
public interface PostGameActionsListener extends EventListener {

    /**
     * This method is called when a new game needs to be initialized
     */
    void reset();

    /**
     * This method is called when a new level selector dialog needs to be opened
     *
     * @return true if the operation terminated successfully
     */
    boolean loadLevel();

    /**
     * This method is called when a new file selector needs to be open to load a previous saved game
     *
     * @return true if the operation terminated successfully
     */
    boolean loadLevelFromFile();

    /**
     * This method terminates the program execution with a 0 code.
     */
    void exit();
}
