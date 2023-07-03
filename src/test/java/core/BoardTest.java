package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import solver.Solver;
import ui.blocks.*;

import java.awt.*;
import java.util.ArrayList;

import static main.Constants.TITLE_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static main.Constants.LAYOUT_DEFAULT;

@SuppressWarnings("PointlessArithmeticExpression")
class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();

        board.setBlocks(new ArrayList<>() {
            {
                add(new WideBlock(2, 0, BlockType.WIDE_HORIZONTAL));
                add(new WideBlock(0, 3, BlockType.WIDE_VERTICAL));
                add(new SmallBlock(3, 4));
                add(new SmallBlock(0, 1));
                add(new LargeBlock(1, 2));
            }
        });
    }


    @Test
    void startGameLayoutTest(){
        Board b2 = new Board(LAYOUT_DEFAULT);
        String status = Solver.getState(b2.getBlocks());
        assertEquals("24422442233221121001", status);
    }

    @Test
    void blockMovedTest() {
        Point startPos = new Point(3 * TITLE_SIZE, 0 * TITLE_SIZE);
        Point endPos = new Point(3 * TITLE_SIZE, 2 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        endPos = new Point(3 * TITLE_SIZE, 1 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(0 * TITLE_SIZE, 3 * TITLE_SIZE);
        endPos = new Point(0 * TITLE_SIZE, 1 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        ArrayList<Block> finalDestination = new ArrayList<>() {
            {
                add(new SmallBlock(0, 1));
                add(new LargeBlock(1, 2));
                add(new WideBlock(2, 1, BlockType.WIDE_HORIZONTAL));
                add(new SmallBlock(3 ,2));
                add(new WideBlock(0, 2, BlockType.WIDE_VERTICAL));
            }
        };

        assertEquals(finalDestination, board.getBlocks());
    }

    @Test
    void gameNotWon() {
        Point startPos = new Point(1 * TITLE_SIZE, 2 * TITLE_SIZE);
        Point endPos = new Point(1 * TITLE_SIZE, 1 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(3 * TITLE_SIZE, 4 * TITLE_SIZE);
        endPos = new Point(1 * TITLE_SIZE, 4 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        startPos = new Point(1 * TITLE_SIZE, 4 * TITLE_SIZE);
        endPos = new Point(1 * TITLE_SIZE, 3 * TITLE_SIZE);
        board.blockMoved(startPos, endPos);

        assertFalse(board.isGameWon());
    }

    @Test
    void gameWon() {
        Point startPos = new Point(1 * TITLE_SIZE, 2 * TITLE_SIZE);
        Point endPos = new Point(1 * TITLE_SIZE, 3 * TITLE_SIZE);

        try {
            board.blockMoved(startPos, endPos);
        } catch (NullPointerException ignored) {
        }

        assertTrue(board.isGameWon());
    }

}