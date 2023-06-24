package io.schemas;

import core.Move;

public class HintSchema {

    private String state;
    private Move bestMove;

    public HintSchema() {
    }

    public String getState() {
        return state;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBestMove(Move bestMove) {
        this.bestMove = bestMove;
    }
}
