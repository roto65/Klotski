package core;

import io.GsonFileParser;
import io.db.MongoDbConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import com.mongodb.MongoException;
import solver.Solver;
import ui.dialogs.DbErrorDialog;

import java.awt.*;

import static main.Constants.LAYOUT_DEFAULT;
import static main.Constants.TITLE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DashboardTest {

    private Board board;
    private String initConfig;
    private MongoDbConnection db;
    private Dashboard dashboard;

    @BeforeEach
    void setUp() {
        board = new Board(LAYOUT_DEFAULT);
        initConfig = Solver.getState(board.getBlocks());
        dashboard = new Dashboard(board);
    }

    @Test
    void NewGameTest() {
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(3 * TITLE_SIZE, 2 * TITLE_SIZE);
        endPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        board.resetBoard();

        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(initConfig, finalDestination);
    }

    @Test
    void SaveLoadTest() {
        GsonFileParser parser = new GsonFileParser("src/test/resources/layout/test.json");
        parser.save(board.getCurrentLevel());

        board.setBlocks(parser.load(true).getBlocks());

        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(initConfig, finalDestination);
    }


    @Test
    void UndoTest() {
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        board.undo();

        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(initConfig, finalDestination);
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
        assertEquals(midDestination, finalDestination );
    }

    @Test
    void HintTest() {
        Move move = Solver.start(board.getBlocks());
        assert move != null;
        board.performMoveUnchecked(move);

       String finalDestination = Solver.getState(board.getBlocks());
       String actualDestination = "24422442233220121101";
       assertEquals(actualDestination, finalDestination);
    }

    @Test
    void LevelSelectorTest(){
        try {
            db = new MongoDbConnection();
        } catch (MongoException e) {
        }
        board.resetBoard(db.getLevel(420));
        db.closeClient();

        String finalDestination = Solver.getState(board.getBlocks());
        String actualDestination = "44124402103312331233";
        assertEquals(actualDestination, finalDestination);
    }

    @Test
    void ResetMoveCounterTest() {
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(3 * TITLE_SIZE, 2 * TITLE_SIZE);
        endPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        board.resetBoard();
        dashboard.resetMoveCounter();
        String Move = dashboard.getMoveCounter().getVariableText();
        assertEquals("0", Move);
    }

    @Test
    void ExitTest() throws Exception {
        int status = SystemLambda.catchSystemExit(() -> {
            dashboard.exit();
        });

        assertEquals(0, status);
    }

    @Test
    void MoveCounterAfterLoadTest() {
        GsonFileParser parser = new GsonFileParser("src/test/resources/layout/test.json");
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(3 * TITLE_SIZE, 2 * TITLE_SIZE);
        endPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);
        parser.save(board.getCurrentLevel());

        board.setBlocks(parser.load(true).getBlocks());
        String FinalMoveCounter = dashboard.getMoveCounter().getVariableText();

        assertEquals("2", FinalMoveCounter);
    }

    @Test
    void LevelLabelAfterLoadTest(){
        GsonFileParser parser = new GsonFileParser("src/test/resources/layout/test.json");
        parser.save(board.getCurrentLevel());
         try {
            db = new MongoDbConnection();
        } catch (MongoException e) {
        }
        board.resetBoard(db.getLevel(420));
        db.closeClient();
        assertEquals(1, parser.load(true).getLevelNumber());
    }

    @Test 
    void NewGameAfterLoadTest(){
        GsonFileParser parser = new GsonFileParser("src/test/resources/layout/test.json");
        Point startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        Point endPos = new Point(2 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(3 * TITLE_SIZE, 2 * TITLE_SIZE);
        endPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);
        parser.save(board.getCurrentLevel());

        board.resetBoard();
        board.loadBoard(parser.load(true));
        board.resetBoard();
        String finalDestination = Solver.getState(board.getBlocks());
        assertEquals(initConfig, finalDestination);
    }
}