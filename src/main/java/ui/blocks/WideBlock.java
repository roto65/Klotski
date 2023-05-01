package ui.blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static main.Constants.*;

public class WideBlock extends Block{

    @SuppressWarnings("unused")
    public WideBlock() {
    }
    public WideBlock(int x, int y, BlockType blockType) {
        pos = new Point(x, y);

        if (blockType != BlockType.WIDE_VERTICAL && blockType != BlockType.WIDE_HORIZONTAL) {
            throw new IllegalArgumentException();
        }
        this.blockType = blockType;

        loadOffsets();
        loadSprite();
    }

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

    @Override
    protected void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/main/resources/drawable/Mid.png"));
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {

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
    }

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

