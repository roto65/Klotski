package ui;

import core.StyledButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

public class StyledButtonUI extends BasicButtonUI {

    private BufferedImage defaultButtonSprite;
    private BufferedImage pressedButtonSprite;

    private Font buttonFont;

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public StyledButtonUI() {
        try {
            defaultButtonSprite = ImageIO.read(new File("src/main/resources/drawable/Button.png"));
            pressedButtonSprite = ImageIO.read(new File("src/main/resources/drawable/PressedButton.png"));
        } catch (IOException e) {
            System.out.println("Error opening image file: " + e.getMessage());
        }

        try {
            buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/DotGothic.ttf"))
                    .deriveFont(Font.BOLD, TITLE_SIZE / 6);
        } catch (IOException | FontFormatException e) {
            System.out.println("Error opening font file: " + e.getMessage());
        }
    }

    @Override
    public void paint(Graphics g, JComponent component) {

        StyledButton button = (StyledButton) component;

        Image scaledSprite;

        if (button.isPressed()) {
            scaledSprite = pressedButtonSprite.getScaledInstance(component.getWidth(), component.getHeight(), Image.SCALE_FAST);
        } else {
            scaledSprite = defaultButtonSprite.getScaledInstance(component.getWidth(), component.getHeight(), Image.SCALE_FAST);
        }

        g.drawImage(scaledSprite, 0, 0, null);

        super.paint(g, button);
    }

    @Override
    protected void paintText(Graphics g, JComponent component, Rectangle textRect, String text) {

        StyledButton button = (StyledButton) component;

        g.setFont(buttonFont);

        int x = textRect.x + (textRect.width - g.getFontMetrics().stringWidth(text)) / 2;
        int y = textRect.y;

        if (button.isPressed()) {
            y += button.getHeight() / 12;
        }

        g.setColor(button.getForeground());
        g.drawString(text, x, y);
    }
}
