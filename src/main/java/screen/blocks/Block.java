package screen.blocks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class Block {

    protected int x, y;
    protected BufferedImage sprite;

    protected abstract void loadSprite();

    public abstract void draw(Graphics g, ImageObserver observer);
}
