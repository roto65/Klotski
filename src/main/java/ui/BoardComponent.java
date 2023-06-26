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

/**
 * Defines the ui for the board class
 */
public class BoardComponent extends JPanel implements MouseListener {

    /**
     * Reference of the block list of the parent board
     */
    private ArrayList<Block> blocks;

    /**
     * Listener used to tell the board when the user made an interaction
     */
    private BlockMoveListener listener;

    /**
     * Coordinates of the user interaction
     */
    @SuppressWarnings("FieldCanBeLocal")
    private static Point startCoord, endCoord;

    /**
     * Constructor method that initializes the data needed to show the component correctly
     *
     * @param blocks a reference of the parent block list
     */
    public BoardComponent(ArrayList<Block> blocks) {
        setPreferredSize(new Dimension(TITLE_SIZE * COLUMNS, TITLE_SIZE * ROWS));

        this.blocks = blocks;
    }

    /**
     * @param listener the parent instance of the board class
     */
    public void setListener(BlockMoveListener listener) {
        this.listener = listener;
    }

    /**
     * @param blocks the reference of a new list of blocks
     */
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * {@inheritDoc}
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);

        for (Block block: blocks) {
            block.draw(g, this);
        }
    }

    /**
     * Method used to draw custom background of the component
     *
     * @param g the graphics object
     */
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

    /**
     * {@inheritDoc}
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("Mouse clicked: " + e);
    }

    /**
     * {@inheritDoc}
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("Mouse pressed: " + e);

        startCoord = e.getPoint();
    }

    /**
     * {@inheritDoc}
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("Mouse released: " + e);

        endCoord = e.getPoint();

        listener.blockMoved(startCoord, endCoord);
    }

    /**
     * {@inheritDoc}
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println("Mouse entered: " + e);
    }

    /**
     * {@inheritDoc}
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("Mouse exited: " + e);
    }
}
