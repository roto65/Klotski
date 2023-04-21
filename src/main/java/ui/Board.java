package ui;

import ui.blocks.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.Constants.*;

public class Board extends JPanel {

    static ArrayList<Block> blocks = new ArrayList<>();


    public Board() {
        setPreferredSize(new Dimension(TITLE_SIZE * COLUMNS, TITLE_SIZE * ROWS));

        setBackground(new Color(46, 91, 255));
        populateBoard();
    }

    private void populateBoard() {
        blocks.add(new WideBlock(0, 0, Facing.VERTICAL));
        blocks.add(new LargeBlock(1, 0));
        blocks.add(new WideBlock(3, 0, Facing.VERTICAL));
        blocks.add(new WideBlock(0, 2, Facing.VERTICAL));
        blocks.add(new WideBlock(1, 2, Facing.HORIZONTAL));
        blocks.add(new WideBlock(3, 2, Facing.VERTICAL));
        blocks.add(new SmallBlock(1, 3));
        blocks.add(new SmallBlock(2, 3));
        blocks.add(new SmallBlock(0, 4));
        blocks.add(new SmallBlock(3, 4));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        System.out.print(blocks.size());

        for (Block block: blocks) {
            block.draw(g, this);
        }
    }
}
