package ui;

import io.Parser;
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

        Parser parser = new Parser("default");

        blocks = parser.load();

        parser.save(blocks);
    }

    @SuppressWarnings("unused")
    private void populateBoard() {
        blocks.add(new WideBlock(0, 0, BlockType.WIDE_VERTICAL));
        blocks.add(new LargeBlock(1, 0));
        blocks.add(new WideBlock(3, 0, BlockType.WIDE_VERTICAL));
        blocks.add(new WideBlock(0, 2, BlockType.WIDE_VERTICAL));
        blocks.add(new WideBlock(1, 2, BlockType.WIDE_HORIZONTAL));
        blocks.add(new WideBlock(3, 2, BlockType.WIDE_VERTICAL));
        blocks.add(new SmallBlock(1, 3));
        blocks.add(new SmallBlock(2, 3));
        blocks.add(new SmallBlock(0, 4));
        blocks.add(new SmallBlock(3, 4));

        Parser parser = new Parser("default", "json");

        parser.save(blocks);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Block block: blocks) {
            System.out.println(block);
            block.draw(g, this);
        }
    }
}
