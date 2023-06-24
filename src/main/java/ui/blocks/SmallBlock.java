package ui.blocks;

import io.IOUtils;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

import static main.Constants.*;

public class SmallBlock extends Block{

    @SuppressWarnings("unused")
    public SmallBlock() {
    }

    public SmallBlock(int x, int y) {
        pos = new Point(x, y);

        blockType = BlockType.SMALL;

        loadOffsets();
        loadSprite();
    }

    @Override
    protected void loadOffsets() {
        offsets = new ArrayList<>() {
            {
                add(new Point(0, 0));
            }
        };
    }

    @Override
    protected void loadSprite() {
        try {
            if (USE_LEGACY_SPRITES) {
                sprite = IOUtils.readFromPng("Small.png");
            } else {
                sprite = IOUtils.readFromPng("SmallBlock.png");
            }
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(sprite, pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
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

        if (pos.x >= COLUMNS) {
            pos.x = COLUMNS - 1;
        }

        if (pos.y >= ROWS) {
            pos.y = ROWS - 1;
        }

    }
}
