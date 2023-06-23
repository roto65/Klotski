package solver;

import core.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static main.Constants.LAYOUT_DEFAULT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NewSolverTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(LAYOUT_DEFAULT);
    }

    @Test
    void getStateTest() {
        String generateState = NewSolver.getState(board.getBlocks());
        String actualState = "24422442233221121001";
        assertEquals(generateState, actualState);
    }
}