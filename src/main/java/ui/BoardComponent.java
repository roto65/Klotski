package ui;

import core.BlockMoveListener;
import ui.blocks.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static main.Constants.*;

public class BoardComponent extends JPanel implements MouseListener {

    private final ArrayList<Block> blocks;

    private BlockMoveListener listener;

    static Point startCoord, endCoord;

    public BoardComponent(ArrayList<Block> blocks) {
        setPreferredSize(new Dimension(TITLE_SIZE * COLUMNS, TITLE_SIZE * ROWS));

        setBackground(new Color(46, 91, 255));

        this.blocks = blocks;
    }

    public void setListener(BlockMoveListener listener) {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Block block: blocks) {
            block.draw(g, this);
        }
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
