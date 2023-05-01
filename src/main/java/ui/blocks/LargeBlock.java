package ui.blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static main.Constants.*;
import static main.Constants.ROWS;

public class LargeBlock extends Block{

    @SuppressWarnings("unused")
    public LargeBlock() {
    }

    public LargeBlock(int x, int y) {
        pos = new Point(x, y);

        blockType = BlockType.LARGE;

        loadOffsets();
        loadSprite();
    }

    @Override
    protected void loadOffsets() {
        offsets = new ArrayList<>() {
            {
                add(new Point(0, 0));
                add(new Point(0, 1));
                add(new Point(1, 0));
                add(new Point(1, 1));
            }
        };
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

    @Override
    public void move(Point destination) {

        pos = destination;

        if (pos.x < 0) {
            pos.x = 0;
        }

        if (pos.y < 0) {
            pos.y = 0;
        }

        if (pos.x >= COLUMNS - 1 ) {
            pos.x = COLUMNS - 2;
        }

        if (pos.y >= ROWS - 1) {
            pos.y = ROWS - 2;
        }


    }
}
