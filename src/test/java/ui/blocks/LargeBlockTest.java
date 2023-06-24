package ui.blocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LargeBlockTest {
    private LargeBlock block;

    @BeforeEach
    void setUp() {
        block = new LargeBlock(1 ,1);
    }

    @Test
    void moveInBound() {
        Point endPos = new Point(2,1);

        block.move(endPos);

        assertEquals(endPos, block.getPos());
    }

    @Test
    void moveOutOfBound() {
        Point endPos = new Point(6, 9);

        block.move(endPos);

        assertEquals(new Point(2, 3), block.getPos());
    }

    @Test
    void moveNegative() {
        Point endPos = new Point(-6, -9);

        block.move(endPos);

        assertEquals(new Point(0, 0), block.getPos());
    }
}
