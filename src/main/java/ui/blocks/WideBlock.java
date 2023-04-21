package ui.blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

public class WideBlock extends Block{

    @SuppressWarnings("unused")
    public WideBlock() {
        loadSprite();
    }
    public WideBlock(int x, int y, BlockType blockType) {
        pos = new Point(x, y);

        if (blockType != BlockType.WIDE_VERTICAL && blockType != BlockType.WIDE_HORIZONTAL) {
            throw new IllegalArgumentException();
        }
        this.blockType = blockType;

        loadSprite();
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
}

