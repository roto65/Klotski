package ui.blocks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public abstract class Block {

    protected Point pos;
    protected transient ArrayList<Point> offsets;
    protected transient BufferedImage sprite; //transient = non serializable
    protected BlockType blockType;
    protected abstract void loadOffsets();
    protected abstract void loadSprite();

    public abstract void draw(Graphics g, ImageObserver observer);

    public abstract void move(Point destination);

    public Point getPos() {
        return pos;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void postDeserializationProcess() {
        loadOffsets();
        loadSprite();
    }

    public ArrayList<Point> getSectionsPos() {
        return new ArrayList<>() {
            {
                for (Point offset : offsets) {
                    add(new Point(pos.x + offset.x, pos.y + offset.y));
                }
            }
        };
    }

    public Block copy() {
        switch (blockType) {
            case SMALL -> {
                return new SmallBlock(pos.x, pos.y);
            }
            case WIDE_HORIZONTAL -> {
                return new WideBlock(pos.x, pos.y, BlockType.WIDE_HORIZONTAL);
            }
            case WIDE_VERTICAL -> {
                return new WideBlock(pos.x, pos.y, BlockType.WIDE_VERTICAL);
            }
            case LARGE -> {
                return new LargeBlock(pos.x, pos.y);
            }
        }
        return null;
    }
}
