package ui.blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

public class WideBlock extends Block{

    private Facing facing; // 0: horizontal, 1: vertical

    public WideBlock(int x, int y, Facing facing) {
        this.x = x;
        this.y = y;

        this.facing = facing;

        loadSprite();
    }

    @Override
    protected void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/main/resources/Mid.png"));
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform backup = g2d.getTransform();

        double locationX, locationY;

        locationX = x * TITLE_SIZE + ((float) TITLE_SIZE / 2);
        locationY = y * TITLE_SIZE + ((float) TITLE_SIZE / 2);


        if (facing == Facing.VERTICAL) {
            g2d.rotate(Math.toRadians(90), locationX, locationY);
        }
            g.drawImage(sprite, x * TITLE_SIZE, y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

            g2d.rotate(Math.toRadians(180), locationX, locationY);
            g.drawImage(sprite, (x - 1) * TITLE_SIZE, y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);

        g2d.setTransform(backup);
    }
}

