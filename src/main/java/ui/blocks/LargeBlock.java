package ui.blocks;

import io.IOUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

import static main.Constants.*;

/**
 * Defines the large block extending the abstract block
 */
public class LargeBlock extends Block{

    /**
     * Default constructor method used in deserialization
     */
    @SuppressWarnings("unused")
    public LargeBlock() {
    }

    /**
     * Constructor method with coordinates
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public LargeBlock(int x, int y) {
        pos = new Point(x, y);

        blockType = BlockType.LARGE;

        loadOffsets();
        loadSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadOffsets() {
        offsets = new ArrayList<>() {
            {
                add(new Point(0, 0));
                add(new Point(0, 1));
                add(new Point(1, 0));
                add(new Point(1, 1));
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadSprite() {
        try {
            if (USE_LEGACY_SPRITES) {
                sprite = IOUtils.readFromPng("Big.png");
            } else {
                sprite = IOUtils.readFromPng("LargeBlock.png");
            }
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param g graphics object
     * @param observer receives information about the objects drawn
     */
    public void draw(Graphics g, ImageObserver observer) {

        if (USE_LEGACY_SPRITES) {
            Graphics2D g2d = (Graphics2D) g;

            AffineTransform backup = g2d.getTransform();

            double locationX, locationY;

            locationX = pos.x * TITLE_SIZE + ((float) TITLE_SIZE / 2);
            locationY = pos.y * TITLE_SIZE + ((float) TITLE_SIZE / 2);

            // tl corner
            g2d.drawImage(sprite, pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

            // tr corner
            g2d.rotate(Math.toRadians(90), locationX, locationY);
            g2d.drawImage(sprite, pos.x * TITLE_SIZE, (pos.y - 1) * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

            //br corner
            g2d.rotate(Math.toRadians(90), locationX, locationY);
            g2d.drawImage(sprite, (pos.x - 1) * TITLE_SIZE, (pos.y - 1) * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

            //bl corner
            g2d.rotate(Math.toRadians(90), locationX, locationY);
            g2d.drawImage(sprite, (pos.x - 1) * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

            g2d.setTransform(backup);
        } else {
            g.drawImage(sprite, pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE * 2, TITLE_SIZE * 2, observer);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param destination the position the block needs to be moved into
     */
    @Override
    public void move(Point destination) {

        pos = destination;

        if (pos.x < 0) {
            pos.x = 0;
        }

        if (pos.y < 0) {
            pos.y = 0;
        }

        if (pos.x >= COLUMNS - 1 ) {
            pos.x = COLUMNS - 2;
        }

        if (pos.y >= ROWS - 1) {
            pos.y = ROWS - 2;
        }


    }
}
