package core;

import core.listener.BlockMoveListener;
import core.listener.MoveCountIncrementListener;
import io.GsonFileParser;
import io.JsonFileChooser;
import io.db.BsonParser;
import solver.NewSolver;
import ui.BoardComponent;
import ui.Window;
import ui.blocks.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import static main.Constants.TITLE_SIZE;
import static main.Constants.USE_SOLVER_DEBUG_PRINT;

public class Board implements BlockMoveListener {

    private static boolean winMessageDisplay = true;

    private String lastConfigName;
    private final BoardComponent boardComponent;

    private ArrayList<Move> moves;
    private ListIterator<Move> movesIterator;

    private MoveCountIncrementListener moveCountIncrementListener;

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

        winMessageDisplay = true;

        Window.newGame(boardComponent);

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

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setMoveCountIncrementListener(MoveCountIncrementListener moveCountIncrementListener) {
        this.moveCountIncrementListener = moveCountIncrementListener;
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

        Point trueStartPos = blockToMove.getPos();
        Point trueEndPos;

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            if (deltaX >= 0) {      // Right move
                trueEndPos = pushBlock(blockToMove, deltaX, Direction.RIGHT);
            } else {                // Left move
                trueEndPos = pushBlock(blockToMove, -deltaX, Direction.LEFT);
            }
        } else {
        if (deltaY >= 0) {          // Down move
                trueEndPos = pushBlock(blockToMove, deltaY, Direction.DOWN);
            } else {                // Up move
                trueEndPos = pushBlock(blockToMove, -deltaY, Direction.UP);
            }
        }

        Move move = new Move(trueStartPos, trueEndPos);

        boardComponent.repaint();

        if (!trueStartPos.equals(trueEndPos)) {
            moves.add(move);
            movesIterator = moves.listIterator(moves.size());

            moveCountIncrementListener.incrementMoveCounter();
        }

        checkWin();
    }

    private void performMove(Move move) {

        int startBlockIndex = linearSearch(move.getStartPos());

        if (startBlockIndex == -1) return;

        Block blockToMove = blocks.get(startBlockIndex);
        blocks.remove(startBlockIndex);

        pushBlock(blockToMove, move.evalSteps(), move.evalDirection());

        boardComponent.repaint();
    }

    public void performMoveUnchecked(Move move) {

        int startBlockIndex = linearSearch(move.getStartPos());

        if (startBlockIndex == -1) return;

        Point offset = PointUtils.subtract(blocks.get(startBlockIndex).getPos(), move.getStartPos());

        move.setStartPos(blocks.get(startBlockIndex).getPos());
        move.setEndPos(PointUtils.add(move.getEndPos(), offset));

        if (USE_SOLVER_DEBUG_PRINT) System.out.println(move);

        if (move.isCut()) {
            moves.addAll(move.evalCutMoves(NewSolver.getState(blocks)));
        } else {
            moves.add(move);
        }
        movesIterator = moves.listIterator(moves.size());

        blocks.get(startBlockIndex).move(move.getEndPos());

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

    private Point pushBlock(Block blockToMove, int steps, Direction direction) {

        /*
         * TODO: forse questa funzione dovrebbe prendere in input l'indice del blocco e non il blocco stesso
         * in questo modo si rende più atomico il tutto e si eliminano le operazioni precedenti alla chiamata della
         * funzione stessa
         */

        Point directionVector = direction.getVector();

        Point endPoint;

        while (steps > 0) {

            for (Point sectionPos : blockToMove.getSectionsPos()){
                Point posToSearch = new Point(sectionPos.x + directionVector.x, sectionPos.y + directionVector.y);
                if (linearSearch(posToSearch) != -1) {
                    blocks.add(blockToMove);
                    return blockToMove.getPos();
                }
            }
            Point currentPos = blockToMove.getPos();

            blockToMove.move(new Point(currentPos.x + directionVector.x, currentPos.y + directionVector.y));

            steps--;
        }

        blocks.add(blockToMove);
        return blockToMove.getPos();
    }

    public boolean checkWin() {
        for (Block block : blocks) {
            if (block.getClass().equals(LargeBlock.class)) {
                Point pos = block.getPos();
                if (pos.x == 1 && pos.y == 3){
                    if (winMessageDisplay) {
                        System.out.println("Hai vinto, sei un figo!");
                    }
                    winMessageDisplay = false;
                    Window.endGame(getBoardComponent());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean undo() {
        if (movesIterator.hasPrevious()) {
            Move previous = movesIterator.previous().reverse();
            performMove(previous);

            return true;
        }
        return false;
    }

    public boolean redo() {
        if (movesIterator.hasNext()) {
            Move next = movesIterator.next();
            performMove(next);

            return  true;
        }
        return false;
    }

    public void save() {
        JsonFileChooser fileChooser = new JsonFileChooser();
        File file = fileChooser.showSaveDialog();

        if (file == null) return;

        GsonFileParser parser = new GsonFileParser(file.getAbsolutePath());

        parser.save(blocks);

    }

    public void load() {

        JsonFileChooser fileChooser = new JsonFileChooser();
        File file = fileChooser.showLoadDialog();

        if (file == null) return;

        GsonFileParser parser = new GsonFileParser(file.getAbsolutePath());

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();

        blocks = parser.load();

        boardComponent.setBlocks(blocks);

        boardComponent.repaint();

    }
}
