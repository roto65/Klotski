package core;

import io.GsonFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import solver.Solver;

import java.awt.*;

import static main.Constants.LAYOUT_DEFAULT;
import static main.Constants.TITLE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DashboardTest {

    private Board board;
    private String initConfig;

    @BeforeEach
    void setUp() {
        board = new Board(LAYOUT_DEFAULT);
        initConfig = Solver.getState(board.getBlocks());
    }

    @Test
    void NewGameTest() {
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(3 * TITLE_SIZE, 2 * TITLE_SIZE);
        endPos = new Point(2 * TITLE_SIZE, 3 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        board.resetBoard();

        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(finalDestination, initConfig);
    }

    @Test
    void SaveLoadTest() {
        GsonFileParser parser = new GsonFileParser("src/test/resources/layout/test.json");
        parser.save(board.getCurrentLevel());

        board.setBlocks(parser.load(true).getBlocks());

        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(finalDestination, initConfig);
    }


    @Test
    void UndoTest() {
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        board.undo();

        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(finalDestination, initConfig);
    }

    @Test
    void RedoTest() {
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        String midDestination = Solver.getState(board.getBlocks());

        board.undo();
        board.redo();

        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(finalDestination, midDestination );
    }

    @Test
    void HintTest() {
        Move move = Solver.start(board.getBlocks());
        assert move != null;
        board.performMoveUnchecked(move);

       String finalDestination = Solver.getState(board.getBlocks());
       String actualDestination = "24422442233220121101";
       assertEquals(finalDestination, actualDestination);
    }
}