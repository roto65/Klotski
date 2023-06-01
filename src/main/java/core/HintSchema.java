package core;

public class HintSchema {

    private String state;
    private Move bestMove;

    public HintSchema() {
    }

    public HintSchema(String state, Move bestMove) {
        this.state = state;
        this.bestMove = bestMove;
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
