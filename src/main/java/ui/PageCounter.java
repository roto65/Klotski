package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static main.Constants.COLOR_BACKGROUND;
import static main.Constants.TITLE_SIZE;

public class PageCounter extends JPanel {
    private final int dotSize = TITLE_SIZE / 10;
    private final int activeDotSize = TITLE_SIZE / 8;
    private final int dotGap = TITLE_SIZE / 8;

    private int currentPage = 1;
    private final int totalPages;

    public PageCounter(int totalPages) {
        setPreferredSize(new Dimension((dotSize + dotGap) * totalPages, activeDotSize));

        setBackground(COLOR_BACKGROUND);

        this.totalPages = totalPages;

    }

    public void setCurrentPage(int page) {
        if (page != currentPage) {
            currentPage = page;
            repaint();
        }
    }

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
                g2d.setColor(new Color(143, 222, 93));
                g2d.fillOval(dotX, 0, activeDotSize, activeDotSize);
            } else {
                // Draw inactive dot
                g2d.setColor(new Color(60, 163, 112));
                g2d.fillOval(dotX, dotY, dotSize, dotSize);
            }
        }

        g2d.setTransform(backup);
    }
}
