package ui;

import core.listener.BlockMoveListener;
import ui.blocks.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static main.Constants.*;

public class BoardComponent extends JPanel implements MouseListener {

    private ArrayList<Block> blocks;

    private BlockMoveListener listener;

    @SuppressWarnings("FieldCanBeLocal")
    private static Point startCoord, endCoord;

    public BoardComponent(ArrayList<Block> blocks) {
        setPreferredSize(new Dimension(TITLE_SIZE * COLUMNS, TITLE_SIZE * ROWS));

        this.blocks = blocks;
    }

    public void setListener(BlockMoveListener listener) {
        this.listener = listener;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);

        for (Block block: blocks) {
            block.draw(g, this);
        }
    }

    private void drawBackground(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform backup = g2d.getTransform();

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (((row == 3 && column == 1) || (row == 4 && column == 2)) && USE_BG_END_HIGHLIGHT) {
                    g2d.setColor(COLOR_BOARD_END_LIGHT);
                } else if (((row == 3 && column == 2) || (row == 4 && column == 1)) && USE_BG_END_HIGHLIGHT) {
                    g2d.setColor(COLOR_BOARD_END_DARK);
                } else if ((row + column) % 2 == 0) {
                    g2d.setColor(COLOR_BOARD_BG_LIGHT);
                } else {
                    g2d.setColor(COLOR_BOARD_BG_DARK);
                }
                g2d.fillRect(column * TITLE_SIZE, row * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE);
            }
        }

        g2d.setTransform(backup);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("Mouse clicked: " + e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("Mouse pressed: " + e);

        startCoord = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("Mouse released: " + e);

        endCoord = e.getPoint();

        listener.blockMoved(startCoord, endCoord);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("Mouse entered: " + e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("Mouse exited: " + e);
    }
}
