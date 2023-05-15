package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StyledButton extends JButton {

    public StyledButton(String text) {
        super(text);

        setContentAreaFilled(false);
        setBorderPainted(false);

        setUI(new StyledButtonUI());
    }
}

class StyledButtonUI extends BasicButtonUI {

    private BufferedImage sprite;

    StyledButtonUI() {
        try {
            sprite = ImageIO.read(new File("src/main/resources/drawable/Button.png"));
        } catch (IOException e) {
            System.out.print("Error opening image file: " + e.getMessage());
        }
    }

    @Override
    public void paint (Graphics g, JComponent component) {

        Image scaledSprite = sprite.getScaledInstance(component.getWidth(), component.getHeight(), Image.SCALE_FAST);
        g.drawImage(scaledSprite, 0, 0, null);

        super.paint(g, (JButton) component);
    }
}