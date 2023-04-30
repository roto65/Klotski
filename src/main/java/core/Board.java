package core;

import io.Parser;
import ui.BoardComponent;
import ui.Window;
import ui.blocks.*;

import java.awt.*;
import java.util.ArrayList;

import static main.Constants.*;

public class Board implements BlockMoveListener {

    private final BoardComponent boardComponent;

    private ArrayList<Block> blocks = new ArrayList<>();
    public Board(String blockConfiguration) {
        initBlocks(blockConfiguration);

        boardComponent = new BoardComponent(blocks);
        boardComponent.setListener(this);
    }

    @SuppressWarnings("SameParameterValue")
    void initBlocks(String filename) {
        Parser parser = new Parser(filename);

        this.blocks = new ArrayList<>(parser.load());
    }

    public BoardComponent getBoardComponent() {
        return boardComponent;
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
    public void blockMoved(Point startCoord, Point endCoord) {

        if (startCoord == null || endCoord == null) {
            return;
        }
        
        Point startPoint = normalizeCord(startCoord);
        Point endPoint = normalizeCord(endCoord);

        int startBlockIndex = linearSearch(startPoint);

        // NO block clicked
        if (startBlockIndex == -1) {
            return;
        }

        // NO destination space
        if (linearSearch(endPoint) != -1) {
            return;
        }

        // Collision check

        blocks.get(startBlockIndex).move(endPoint);

        boardComponent.repaint();

        checkWin();
    }

    private static Point normalizeCord(Point input) {

        int x = input.x / TITLE_SIZE;
        int y = input.y / TITLE_SIZE;

        return new Point(x, y);
    }

    private int linearSearch(Point point) {

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getPos().equals(point))
                return i;
        }
        return -1;
    }

    private void checkWin() {
        for (Block block : blocks) {
            if (block.getClass().equals(LargeBlock.class)) {
                Point pos = block.getPos();
                if (pos.x == 1 && pos.y == 3){
                    System.out.println("Hai vinto, sei un figo!");
                    Window.endGame(getBoardComponent());
                }
            }
        }
    }
}
