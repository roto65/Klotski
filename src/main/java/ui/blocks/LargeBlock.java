package ui.blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

public class LargeBlock extends Block{

    @SuppressWarnings("unused")
    public LargeBlock() {
        loadSprite();
    }

    public LargeBlock(int x, int y) {
        pos = new Point(x, y);

        blockType = BlockType.LARGE;

        loadSprite();
    }

    @Override
    protected void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/main/resources/drawable/Big.png"));
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
    }
}
