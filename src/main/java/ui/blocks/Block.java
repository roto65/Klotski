package ui.blocks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class Block {

    protected Point pos;
    protected transient BufferedImage sprite; //transient = non serializable
    protected BlockType blockType;
    protected abstract void loadSprite();

    public abstract void draw(Graphics g, ImageObserver observer);

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }
}
