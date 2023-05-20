package ui;

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
            sprite = ImageIO.read(new File("src/main/resources/drawable/PressedButton.png"));
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
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }

    @Override
    protected void paintEnabledText(JLabel label, Graphics g, String text, int textX, int textY) {
        g.setFont(labelFont);
        g.setColor(label.getForeground());
        g.drawString(text, textX, textY);
    }
}
