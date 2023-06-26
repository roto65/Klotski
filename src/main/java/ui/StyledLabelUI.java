package ui;

import core.StyledLabel;
import io.IOUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.io.IOException;

import static main.Constants.TITLE_SIZE;

/**
 * Defines a custom ui used in custom labels
 *
 * @see StyledLabel
 */
public class StyledLabelUI extends BasicLabelUI {

    /**
     * The label's sprite
     */
    private static Image sprite = null;

    /**
     * The font used for the label text
     */
    private static Font labelFont = null;

    /**
     * Constructor method that initializes the label's resources
     */
    public StyledLabelUI() {
        loadResources();
    }

    /**
     * Method that actually loads the label's resources from file
     */
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private void loadResources() {
        if (sprite == null) {
            try {
                sprite = IOUtils.readFromPng("MoveCounter2.png");
            } catch (IOException e) {
                System.out.println("Error opening image file: " + e.getMessage());
            }
        }

        if (labelFont == null) {
            try {
                labelFont = IOUtils.readFromTtf("DotGothic.ttf").deriveFont(Font.BOLD, TITLE_SIZE / 6);
            } catch (IOException | FontFormatException e) {
                System.out.println("Error opening font file: " + e.getMessage());
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @param component the component being painted;
     *          this argument is often ignored,
     *          but might be used if the UI object is stateless
     *          and shared by multiple components
     *
     */
    @Override
    public void paint(Graphics g, JComponent component) {

        StyledLabel label = (StyledLabel) component;

        Image scaledSprite = sprite.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_FAST);

        g.drawImage(scaledSprite, 0, 0, null);

        super.paint(g, component);
    }

    /**
     * {@inheritDoc}
     *
     * @param label an instance of {@code JLabel}
     * @param g an instance of {@code Graphics}
     * @param text a text
     * @param textX an X coordinate
     * @param textY an Y coordinate
     */
    @Override
    protected void paintEnabledText(JLabel label, Graphics g, String text, int textX, int textY) {
        g.setFont(labelFont);

        // Get the FontMetrics to calculate the text position
        FontMetrics metrics = g.getFontMetrics(labelFont);
        int width = label.getWidth();
        int textWidth = metrics.stringWidth(text);

        // Calculate the x-position of the text to center it horizontally
        int x = (width - textWidth) / 2;

        // Calculate the y-position of the text to center it vertically
        int y = metrics.getAscent() + TITLE_SIZE / 12;

        g.setColor(label.getForeground());
        g.drawString(text, x, y);
    }
}
