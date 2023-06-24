package core;

import core.listener.BlockMoveListener;
import core.listener.MovePerformedListener;
import io.GsonFileParser;
import io.schemas.LevelSchema;
import solver.Solver;
import ui.BoardComponent;
import ui.Window;
import ui.blocks.Block;
import ui.blocks.LargeBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

import static main.Constants.TITLE_SIZE;
import static main.Constants.USE_SOLVER_DEBUG_PRINT;

public class Board implements BlockMoveListener {

    private ArrayList<Block> lastConfiguration;

    private boolean gameWon = false;

    private final BoardComponent boardComponent;

    private ArrayList<Move> moves;
    private ListIterator<Move> movesIterator;

    private MovePerformedListener movePerformedListener;

    private ArrayList<Block> blocks;

    private int currentLevelNumber;
    private int minimumMoves;

    public Board() {
        boardComponent = new BoardComponent(null);
        boardComponent.setListener(this);

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();
    }

    public Board(String blockConfiguration) {
        initBlocks(blockConfiguration);

        boardComponent = new BoardComponent(blocks);
        boardComponent.setListener(this);

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();
    }

    public void resetBoard() {

        gameWon = false;
        try {
            Window.newGame(boardComponent);
        } catch(NullPointerException ignored){}

        blocks = new ArrayList<>();

        for (Block block : lastConfiguration) {
            blocks.add(block.copy());
        }

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();

        boardComponent.setBlocks(blocks);

        boardComponent.repaint();
    }

    public void resetBoard(LevelSchema newLevel) {

        gameWon = false;
        try {
            Window.newGame(boardComponent);
        } catch(NullPointerException ignored){}

        blocks = new ArrayList<>();
        lastConfiguration = new ArrayList<>();

        for (Block block : newLevel.getBlocks()) {
            blocks.add(block.copy());
            lastConfiguration.add(block.copy());
        }

        currentLevelNumber = newLevel.getLevelNumber();
        minimumMoves = newLevel.getMinimumMoves();

        moves = newLevel.getMoves();

        if (moves == null) {
            moves = new ArrayList<>();
            movesIterator = moves.listIterator();

        } else {
            movesIterator = moves.listIterator(newLevel.getIteratorIndex());
        }
        boardComponent.setBlocks(blocks);

        boardComponent.repaint();
    }

    private void initBlocks(String filename) {
        GsonFileParser parser = new GsonFileParser(filename, "json");

        LevelSchema levelSchema = parser.load(false);

        blocks = new ArrayList<>();
        lastConfiguration = new ArrayList<>();

        for (Block block : levelSchema.getBlocks()) {
            blocks.add(block.copy());
            lastConfiguration.add(block.copy());
        }

        currentLevelNumber = levelSchema.getLevelNumber();
        minimumMoves = levelSchema.getMinimumMoves();
    }

    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public LevelSchema getCurrentLevel() {
        return new LevelSchema(currentLevelNumber, blocks, minimumMoves, moves, movesIterator.nextIndex());
    }

    public int getMinimumMoves() {
        return minimumMoves;
    }

    public void setMoveCountIncrementListener(MovePerformedListener movePerformedListener) {
        this.movePerformedListener = movePerformedListener;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public void blockMoved(Point startCoord, Point endCoord) {

        if (startCoord == null || endCoord == null) return;

        Point startPoint = normalizeCoord(startCoord);
        Point endPoint   = normalizeCoord(endCoord);

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

            try {
                movePerformedListener.incrementMoveCounter();
            } catch (NullPointerException ignored) {
            }
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
            move = move.evalCutMove(Solver.getState(blocks));
        }
        moves.add(move);

        movesIterator = moves.listIterator(moves.size());

        blocks.get(startBlockIndex).move(move.getEndPos());

        boardComponent.repaint();

        checkWin();
    }

    private static Point normalizeCoord(Point input) {

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

        Point directionVector = direction.getVector();

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

    public void checkWin() {
        for (Block block : blocks) {
            if (block.getClass().equals(LargeBlock.class)) {
                Point pos = block.getPos();
                if (pos.x == 1 && pos.y == 3){
                    gameWon = true;
                    Window.endGame(getBoardComponent());
                    movePerformedListener.triggerPostGame();
                }
            }
        }
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
}
