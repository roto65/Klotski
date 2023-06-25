package io.schemas;

import core.Move;

/**
 * Defines a schema for the hint collection inside the database
 */
public class HintSchema {

    /**
     * Disposition of the board for the given hint
     */
    private String state;

    /**
     * Best move the player can do when the board corresponds to the state stored in the state string
     */
    private Move bestMove;

    /**
     * Constructor called by the codec of this class
     */
    public HintSchema() {
    }

    /**
     * @return the state associated with this hint
     */
    public String getState() {
        return state;
    }

    /**
     * @return the best move that the player can do in the given state
     */
    public Move getBestMove() {
        return bestMove;
    }

    /**
     * @param state new value that needs to replace the old one
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @param bestMove new value that needs to replace the old one
     */
    public void setBestMove(Move bestMove) {
        this.bestMove = bestMove;
    }
}
