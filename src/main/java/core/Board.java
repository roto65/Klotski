package core;

import core.listener.BlockMoveListener;
import core.listener.MovePerformedListener;
import io.GsonFileParser;
import io.db.MongoDbConnection;
import io.schemas.LevelSchema;
import solver.Solver;
import ui.BoardComponent;
import ui.Window;
import ui.blocks.Block;
import ui.blocks.LargeBlock;
import ui.dialogs.DbErrorDialog;

import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

import com.mongodb.MongoException;

import static main.Constants.TITLE_SIZE;
import static main.Constants.USE_SOLVER_DEBUG_PRINT;

/**
 * Defines all the game core functionality, including block managing and moving
 */
public class Board implements BlockMoveListener {

    /**
     * List that stores the initial block configuration of the current game, so it can be restored at any point
     */
    private ArrayList<Block> lastConfiguration;

    /**
     * Variable that indicates if the player won the current game
     */
    private boolean gameWon = false;

    /**
     * Objects that represents the GUI part of the Board itself
     *
     * @see BoardComponent
     */
    private final BoardComponent boardComponent;

    /**
     * List that stores all the moves made by the player. Used by the undo / redo methods
     *
     * @see #undo()
     * @see #redo()
     */
    private ArrayList<Move> moves;

    /**
     * Iterator used for the moves list
     *
     * @see #moves
     */
    private ListIterator<Move> movesIterator;

    /**
     * Listener used to trigger certain methods execution after a block is moved
     */
    private MovePerformedListener movePerformedListener;

    /**
     * List that holds all the objects relative to the blocks present on the board
     */
    private ArrayList<Block> blocks;

    /**
     * Variable that stores the number of the level the player is playing
     */
    private int currentLevelNumber;

    /**
     * Variable that stores the minimum number of moves required for the current level to be completed
     */
    private int minimumMoves;

    /**
     * Default constructor, used mainly for testing purposes
     */
    public Board() {
        boardComponent = new BoardComponent(null);
        boardComponent.setListener(this);

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();
    }

    /**
     * Method that initializes th Board object, his component and all the data structures needed for a normal game
     *
     * @param blockConfiguration the name of the Json file that contains the configuration info needed when the game
     *                           starts
     * @see BoardComponent
     */
    public Board(String blockConfiguration) {
        initBlocks(blockConfiguration);

        boardComponent = new BoardComponent(blocks);
        boardComponent.setListener(this);

        moves = new ArrayList<>();
        movesIterator = moves.listIterator();
    }

    /**
     * Method that resets the Board object back to the start of the level
     */
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

    /**
     * Method that resets the Board object with the data passed as argument
     *
     * @param newLevel object that contains all the data needed to change level
     * @see LevelSchema
     */
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
  
    /**
     * Method that initializes the blocks list with the data red from a Json file
     *
     * @param filename name of the file to read
     * @see GsonFileParser
     */    
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
  
  
    public void loadBoard(LevelSchema newLevel) {

        gameWon = false;
        try {
            Window.newGame(boardComponent);
        } catch(NullPointerException ignored){}

        blocks = new ArrayList<>();
        lastConfiguration = new ArrayList<>();

        for (Block block : newLevel.getBlocks()) {
            blocks.add(block.copy());
        }
        MongoDbConnection db;
        try {
            db = new MongoDbConnection();
            lastConfiguration = db.getLevel(newLevel.getLevelNumber()).getBlocks();
            db.closeClient();
        } catch (MongoException e) {
            new DbErrorDialog(e.getMessage()).showDialog();
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

    /**
     * @return ui component bound to this board instance
     */
    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

    /**
     * @return list of the blocks currently displayed on the board
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * @return true if the player won the game
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * @return a new LevelSchema object containing all the relevant game status information
     * @see LevelSchema
     */
    public LevelSchema getCurrentLevel() {
        return new LevelSchema(currentLevelNumber, blocks, minimumMoves, moves, movesIterator.nextIndex());
    }

    /**
     * @return the minimum number of moves needed to win in the current level
     */
    public int getMinimumMoves() {
        return minimumMoves;
    }

    /**
     * @param movePerformedListener new listener to bind with this board instance
     */
    public void setMoveCountIncrementListener(MovePerformedListener movePerformedListener) {
        this.movePerformedListener = movePerformedListener;
    }

    /**
     * @param blocks new block list that needs to replace the existing one
     */
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * This method contains all the logic needed to execute a block move with complete checks
     *
     * @param startCoord The coordinate where the actions started
     * @param endCoord The coordinate where the action ended
     */
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

    /**
     * This method prepares the block and the list for the changes needed to actuate a move
     *
     * @param move the movement that needs to be done
     */
    private void performMove(Move move) {

        int startBlockIndex = linearSearch(move.getStartPos());

        if (startBlockIndex == -1) return;

        Block blockToMove = blocks.get(startBlockIndex);
        blocks.remove(startBlockIndex);

        pushBlock(blockToMove, move.evalSteps(), move.evalDirection());

        boardComponent.repaint();
    }

    /**
     * This method executes directly a move bypassing all the checks usually needed
     *
     * @param move the movement that needs to be done
     */
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

    /**
     * Methods that converts global window coordinates to board coordinates
     *
     * @param input the coordinates received from the ui component
     * @return coordinates usable inside the board
     */
    private static Point normalizeCoord(Point input) {

        int x = input.x / TITLE_SIZE;
        int y = input.y / TITLE_SIZE;

        return new Point(x, y);
    }

    /**
     * Method that searches which block (if any) occupies a position
     *
     * @param point the position of the block
     * @return the index of the block list where the found block object is placed
     */
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

    /**
     * Method that tries to move a given block, one step at a time
     *
     * @param blockToMove the block that needs to be moved removed from the list
     * @param steps the number of spaces the block needs to be moved
     * @param direction the direction the block needs to be moved
     * @return the position the block moved sits after the movement
     */
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

    /**
     * Method that checks if the win condition is met
     */
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

    /**
     * Method that undoes the last move the player has done
     * @return true if the move was actually undone
     */
    public boolean undo() {
        if (movesIterator.hasPrevious()) {
            Move previous = movesIterator.previous().reverse();
            performMove(previous);

            return true;
        }
        return false;
    }

    /**
     * Method that redoes the last move the player has undone
     * @return true if the move was actually redone
     */
    public boolean redo() {
        if (movesIterator.hasNext()) {
            Move next = movesIterator.next();
            performMove(next);

            return  true;
        }
        return false;
    }
}
