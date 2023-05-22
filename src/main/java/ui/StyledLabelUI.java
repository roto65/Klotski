package ui;

import core.StyledLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

public class StyledLabelUI extends BasicLabelUI {

    private BufferedImage sprite;

    private Font labelFont;

    public StyledLabelUI() {
        try {
            sprite = ImageIO.read(new File("src/main/resources/drawable/MoveCounter2.png"));
        } catch (IOException e) {
            System.out.println("Error opening image file: " + e.getMessage());
        }

        try {
            labelFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/DotGothic.ttf"))
                    .deriveFont(Font.BOLD, TITLE_SIZE / 6);
        } catch (IOException | FontFormatException e) {
            System.out.println("Error opening font file: " + e.getMessage());
        }
    }

    @Override
    public void paint(Graphics g, JComponent component) {

        StyledLabel label = (StyledLabel) component;

        Image scaledSprite = sprite.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_FAST);

        g.drawImage(scaledSprite, 0, 0, null);

        super.paint(g, component);
    }

    @Override
    protected void paintEnabledText(JLabel label, Graphics g, String text, int textX, int textY) {
        g.setFont(labelFont);

        // Get the FontMetrics to calculate the text position
        FontMetrics metrics = g.getFontMetrics(labelFont);
        int width = label.getWidth();
        int height = label.getHeight();
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();

        // Calculate the x-position of the text to center it horizontally
        int x = (width - textWidth) / 2;

        // Calculate the y-position of the text to center it vertically
        int y = metrics.getAscent() + TITLE_SIZE / 12;

        g.setColor(label.getForeground());
        g.drawString(text, x, y);
    }
}
