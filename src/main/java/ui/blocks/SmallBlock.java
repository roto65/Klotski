package ui.blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

public class SmallBlock extends Block{

    public SmallBlock(int x, int y) {
        this.x = x;
        this.y = y;

        loadSprite();
    }

    @Override
    protected void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/main/resources/Small.png"));
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(sprite, x * TITLE_SIZE, y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
    }
}
