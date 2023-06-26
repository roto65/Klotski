package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static main.Constants.*;

/**
 * Defines a custom ui element used to indicate which page is currently shown
 *
 * @see CardLayout
 */
public class PageCounter extends JPanel {

    /**
     * The dimension of one dot when inactive
     */
    private final int dotSize = TITLE_SIZE / 10;

    /**
     * The dimension of one dot when active
     */
    private final int activeDotSize = TITLE_SIZE / 8;

    /**
     * The distance between dots
     */
    private final int dotGap = TITLE_SIZE / 8;

    /**
     * The number of the dot currently highlighted.
     * Count starts from 1
     */
    private int currentPage = 1;

    /**
     * The number of dots in the counter
     */
    private final int totalPages;

    /**
     * Constructor method that initializes the counter data
     *
     * @param totalPages the number of dots in the counter
     */
    public PageCounter(int totalPages) {
        setPreferredSize(new Dimension((dotSize + dotGap) * totalPages, activeDotSize));

        setBackground(COLOR_BACKGROUND);

        this.totalPages = totalPages;

    }

    /**
     * Method used to change the dot that is currently active
     *
     * @param page the index of the new active dot
     */
    public void setCurrentPage(int page) {
        if (page != currentPage) {
            currentPage = page;
            repaint();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform backup = g2d.getTransform();

        for (int i = 1; i <= totalPages; i++) {
            int dotX = (dotSize + dotGap) * (i - 1);
            int dotY = (activeDotSize - dotSize) / 2;

            if (i == currentPage) {
                // Draw active dot
                g2d.setColor(COLOR_PC_ACTIVE);
                g2d.fillOval(dotX, 0, activeDotSize, activeDotSize);
            } else {
                // Draw inactive dot
                g2d.setColor(COLOR_PC_INACTIVE);
                g2d.fillOval(dotX, dotY, dotSize, dotSize);
            }
        }

        g2d.setTransform(backup);
    }
}
