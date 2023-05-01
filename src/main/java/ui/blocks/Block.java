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

    public void postDeserializationProcess() {
        loadOffsets();
        loadSprite();
    }
}
