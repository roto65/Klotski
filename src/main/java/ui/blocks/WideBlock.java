package ui.blocks;

import io.IOUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

import static main.Constants.*;

/**
 * Defines the wide block extending the abstract block
 */
public class WideBlock extends Block{

    /**
     * Additional sprite for the other rotation of the block
     */
    private transient Image altSprite;

    /**
     * Default constructor method used in deserialization
     */
    @SuppressWarnings("unused")
    public WideBlock() {
    }

    /**
     * Constructor method with coordinates
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param blockType the type of the block, needed for orientation
     */
    public WideBlock(int x, int y, BlockType blockType) {
        pos = new Point(x, y);

        if (blockType != BlockType.WIDE_VERTICAL && blockType != BlockType.WIDE_HORIZONTAL) {
            throw new IllegalArgumentException();
        }
        this.blockType = blockType;

        loadOffsets();
        loadSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadOffsets() {
        switch (blockType) {
            case WIDE_HORIZONTAL -> offsets = new ArrayList<>() {
                {
                    add(new Point(0, 0));
                    add(new Point(1, 0));
                }
            };
            case WIDE_VERTICAL -> offsets = new ArrayList<>() {
                {
                    add(new Point(0, 0));
                    add(new Point(0, 1));
                }
            };
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadSprite() {
        try {
            if (USE_LEGACY_SPRITES) {
                sprite = IOUtils.readFromPng("Mid.png");
            } else {
                sprite = IOUtils.readFromPng("WideBlock.png");
                altSprite = IOUtils.readFromPng("TallBlock.png");
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


            if (blockType == BlockType.WIDE_VERTICAL) {
                g2d.rotate(Math.toRadians(90), locationX, locationY);
            }
            g.drawImage(sprite, pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

            g2d.rotate(Math.toRadians(180), locationX, locationY);
            g.drawImage(sprite, (pos.x - 1) * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

            g2d.setTransform(backup);
        } else {
            if (blockType == BlockType.WIDE_HORIZONTAL) {
                g.drawImage(sprite, pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE * 2, TITLE_SIZE, observer);
            } else if (blockType == BlockType.WIDE_VERTICAL) {
                g.drawImage(altSprite, pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE * 2, observer);
            }
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

        switch (blockType) {
            case WIDE_VERTICAL -> {
                if (pos.x >= COLUMNS) {
                    pos.x = COLUMNS - 1;
                }
                if (pos.y >= ROWS - 1) {
                    pos.y = ROWS - 2;
                }
            }
            case WIDE_HORIZONTAL -> {
                if (pos.x >= COLUMNS - 1) {
                    pos.x = COLUMNS - 2;
                }
                if (pos.y >= ROWS) {
                    pos.y = ROWS - 1;
                }
            }
        }
    }
}

