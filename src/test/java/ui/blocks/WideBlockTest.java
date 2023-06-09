package ui.blocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WideBlockTest {

    private WideBlock horizontalBlock;
    private WideBlock verticalBlock;

    @BeforeEach
    void setUp() {
        horizontalBlock = new WideBlock(1, 1, BlockType.WIDE_HORIZONTAL);
        verticalBlock = new WideBlock(1, 1, BlockType.WIDE_VERTICAL);
    }

    @Test
    void moveInBoundHorizontal() {
        Point endPos = new Point(2,1);

        horizontalBlock.move(endPos);

        assertEquals(endPos, horizontalBlock.getPos());
    }

    @Test
    void moveInBoundVertical() {
        Point endPos = new Point(2,1);

        verticalBlock.move(endPos);

        assertEquals(endPos, verticalBlock.getPos());
    }

    @Test
    void moveOutOfBoundHorizontal() {
        Point endPos = new Point(6, 9);

        horizontalBlock.move(endPos);

        assertEquals(new Point(2, 4), horizontalBlock.getPos());
    }

    @Test
    void moveOutOfBoundVertical() {
        Point endPos = new Point(6, 9);

        verticalBlock.move(endPos);

        assertEquals(new Point(3, 3), verticalBlock.getPos());
    }

    @Test
    void moveNegativeHorizontal() {
        Point endPos = new Point(-6, -9);

        horizontalBlock.move(endPos);

        assertEquals(new Point(0, 0), horizontalBlock.getPos());
    }

    @Test
    void moveNegativeVertical() {
        Point endPos = new Point(-6, -9);

        verticalBlock.move(endPos);

        assertEquals(new Point(0, 0), verticalBlock.getPos());
    }
}
