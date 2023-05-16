package core;

import io.GsonFileParser;
import io.db.BsonParser;
import ui.BoardComponent;
import ui.Window;
import ui.blocks.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

import static main.Constants.TITLE_SIZE;

public class Board implements BlockMoveListener {

    private String lastConfigName;
    private BoardComponent boardComponent;

    private ArrayList<Move> moves;
    private ListIterator<Move> movesIterator;

    private ArrayList<Block> blocks = new ArrayList<>();
    public Board(String blockConfiguration) {
        lastConfigName = blockConfiguration;
        initBlocks(blockConfiguration);

        boardComponent = new BoardComponent(blocks);
        boardComponent.setListener(this);

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();
    }

    public void resetBoard() {

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();

        initBlocks(lastConfigName);

        boardComponent.setBlocks(blocks);

        boardComponent.repaint();
    }

    @SuppressWarnings("SameParameterValue")
    private void initBlocks(String filename) {

        //GsonFileParser parser = new GsonFileParser(filename);

        BsonParser parser = new BsonParser();

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

        GsonFileParser parser = new GsonFileParser("default", "json");

        parser.save(blocks);
    }

    @Override
    public void blockMoved(Point startCoord, Point endCoord) {

        if (startCoord == null || endCoord == null) return;

        Point startPoint = normalizeCord(startCoord);
        Point endPoint   = normalizeCord(endCoord);

        // Mono axis move
        int deltaX = endPoint.x - startPoint.x;
        int deltaY = endPoint.y - startPoint.y;

        int startBlockIndex = linearSearch(startPoint);

        // NO block clicked
        if (startBlockIndex == -1) return;

        Block blockToMove = blocks.get(startBlockIndex);
        blocks.remove(startBlockIndex);

        Move move;

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            if (deltaX >= 0) {      // Right move
                pushBlock(blockToMove, deltaX, Direction.RIGHT);
                move = new Move(startPoint, deltaX, Direction.RIGHT);
            } else {                // Left move
                pushBlock(blockToMove, -deltaX, Direction.LEFT);
                move = new Move(startPoint, -deltaX, Direction.LEFT);
            }
        } else {
        if (deltaY >= 0) {          // Down move
                pushBlock(blockToMove, deltaY, Direction.DOWN);
                move = new Move(startPoint, deltaY, Direction.DOWN);
            } else {                // Up move
                pushBlock(blockToMove, -deltaY, Direction.UP);
                move = new Move(startPoint, -deltaY, Direction.UP);
            }
        }

        boardComponent.repaint();

        moves.add(move);
        movesIterator = moves.listIterator(moves.size());

        checkWin();
    }

    private void performMove(Move move) {

        int startBlockIndex = linearSearch(move.getStartPos());

        if (startBlockIndex == -1) return;

        Block blockToMove = blocks.get(startBlockIndex);
        blocks.remove(startBlockIndex);

        pushBlock(blockToMove, move.getSteps(), move.getDirection());

        boardComponent.repaint();
    }

    private static Point normalizeCord(Point input) {

        int x = input.x / TITLE_SIZE;
        int y = input.y / TITLE_SIZE;

        return new Point(x, y);
    }

    private int linearSearch(Point point) {

        for (int i = 0; i < blocks.size(); i++) {
            for (Point sectionPos : blocks.get(i).getSectionsPos()) {
                if (sectionPos.equals(point)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void pushBlock(Block blockToMove, int steps, Direction direction) {

        /*
         * TODO: forse questa funzione dovrebbe prendere in input l'indice del blocco e non il blocco stesso
         * in questo modo si rende più atomico il tutto e si eliminano le operazioni precedenti alla chiamata della
         * funzione stessa
         */


        Point directionVector = direction.getVector();

        while (steps > 0) {

            for (Point sectionPos : blockToMove.getSectionsPos()){
                Point posToSearch = new Point(sectionPos.x + directionVector.x, sectionPos.y + directionVector.y);
                if (linearSearch(posToSearch) != -1) {
                    blocks.add(blockToMove);
                    return;
                }
            }
            Point currentPos = blockToMove.getPos();

            blockToMove.move(new Point(currentPos.x + directionVector.x, currentPos.y + directionVector.y));

            steps--;
        }
        blocks.add(blockToMove);
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

    public void undo() {
        if (movesIterator.hasPrevious()) {
            Move previous = movesIterator.previous().reverse();
            performMove(previous);
        }
    }

    public void redo() {
        if (movesIterator.hasNext()) {
            Move next = movesIterator.next();
            performMove(next);
        }

    }
}
