package ui.blocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

public class SmallBlock extends Block{

    @SuppressWarnings("unused")
    public SmallBlock() {
        loadSprite();
    }

    public SmallBlock(int x, int y) {
        pos = new Point(x, y);

        blockType = BlockType.SMALL;

        loadSprite();
    }

    @Override
    protected void loadSprite() {
        try {
            sprite = ImageIO.read(new File("src/main/resources/drawable/Small.png"));
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(sprite, pos.x * TITLE_SIZE, pos.y * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE, observer);
    }
}
