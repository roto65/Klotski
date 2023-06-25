package solver;

import ui.blocks.Block;

import java.awt.*;

import static main.Constants.COLUMNS;
import static main.Constants.ROWS;
import static solver.Solver.board;
import static solver.Solver.state;

/**
 * Defines a smaller version of the Block class with fewer information stored to speed up the solver execution
 *
 * @see Block
 */
public class SolverPiece {

    /**
     * Defines x position of the piece
     */
    private int x;

    /**
     * Defines y position of the piece
     */
    private int y;

    /**
     * Defines the width of the piece
     */
    private final int width;

    /**
     * Defines the height of the piece
     */
    private final int height;

    /**
     * Defines the type of the piece
     */
    private final int info;

    /**
     * Constructor method for a piece
     *
     * @param y y coordinate
     * @param x x coordinate
     * @param width width of the piece
     * @param height height of the piece
     * @param info type of the piece
     */
    public SolverPiece(int y, int x, int width, int height, int info) {
        this.width = width;
        this.height = height;
        this.info = info;

        setYX(y, x);
    }

    /**
     * Constructor method that gets piece information form a block
     *
     * @param block block that needs to be cast to a piece
     * @param info type of the piece
     */
    public SolverPiece(Block block, int info) {
        this.width = block.getBlockType().getWidth();
        this.height = block.getBlockType().getHeight();
        this.info = info;

        setYX(block.getPos());
    }

    /**
     * @return the type of the piece
     */
    private int getInfo() {
        return info;
    }

    /**
     * Notation for pieces of the board
     * width x height -> notation
     * 0x0 -> 0  ; 1x1 -> 1  ; 1x2 -> 2 ; 2x1 -> 3 ; 2x2 -> 4
     *
     * @return the size of the piece described as one int
     */
    private int getNotation() {
        if (width == 1 && height == 1) return 1;
        if (width == 1 && height == 2) return 2;
        if (width == 2 && height == 1) return 3;
        if (width == 2 && height == 2) return 4;
        return 0;
    }

    /**
     * Sets the x and y coordinates of the piece and updates the board / state arrays of the Solver
     *
     * @param pos piece position as a Point
     */
    private void setYX(Point pos) {
        this.x = pos.x;
        this.y = pos.y;

        // Set the state of the board based on the piece notation
        int n = getNotation();
        int m = getInfo();

        for (int i = this.y; i < this.y + height; i++)
            for (int j = this.x; j < this.x + width; j++)
                state[i][j] = n;

        for (int i = this.y; i < this.y + height; i++)
            for (int j = this.x; j < this.x + width; j++)
                board[i][j] = m;
    }

    /**
     * Sets the x and y coordinates of the piece and updates the board / state arrays of the Solver
     *
     * @param x x coordinate of the piece
     * @param y y coordinate of the piece
     */
    private void setYX(int y, int x) {
        this.x = x;
        this.y = y;

        // Set the state of the board based on the piece notation
        int n = getNotation();
        int m = getInfo();

        for (int i = this.y; i < this.y + height; i++)
            for (int j = this.x; j < this.x + width; j++)
                state[i][j] = n;

        for (int i = this.y; i < this.y + height; i++)
            for (int j = this.x; j < this.x + width; j++)
                board[i][j] = m;
    }

    /**
     * Method that checks if the piece can be moved to the left
     *
     * @return true if it's possible
     */
    public boolean left() {
        if (x == 0) return false; // If it is touching the left border
        // If it has height 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y][x - 1] == 0 && state[y + height - 1][x - 1] == 0;
    }

    /**
     * Method that checks if the piece can be moved to the right
     *
     * @return true if it's possible
     */
    public boolean right() {
        if (x + width == COLUMNS) return false; // If it is touching the right border
        // If it has height 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y][x + width] == 0 && state[y + height - 1][x + width] == 0;
    }

    /**
     * Method that checks if the piece can be moved up
     *
     * @return true if it's possible
     */
    public boolean up() {
        if (y == 0) return false; // If it is touching the top border
        // If it has width 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y - 1][x] == 0 && state[y - 1][x + width - 1] == 0;
    }

    /**
     * Method that checks if the piece can be moved down
     *
     * @return true if it's possible
     */
    public boolean down() {
        if (y + height == ROWS) return false; // If it is touching the bottom border
        // If it has width 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y + height][x] == 0 && state[y + height][x + width - 1] == 0;
    }

    /**
     * Method that moves the piece left, if it is possible
     */
    public void moveLeft() {
        if (!left()) return;  // Check if possible to move
        // Make move to the left and actualize board
        state[y][x + width - 1] = 0;
        state[y + height - 1][x + width - 1] = 0;
        board[y][x + width - 1] = -1;
        board[y + height - 1][x + width - 1] = -1;
        state[y][x - 1] = getNotation();
        state[y + height - 1][x - 1] = getNotation();
        board[y][x - 1] = getInfo();
        board[y + height - 1][x - 1] = getInfo();
        x--;
    }

    /**
     * Method that moves the piece right, if it is possible
     */
    public void moveRight() {
        if (!right()) return; // Check if possible to move
        // Make move to the right and actualize board
        state[y][x] = 0;
        state[y + height - 1][x] = 0;
        board[y][x] = -1;
        board[y + height - 1][x] = -1;
        state[y][x + width] = getNotation();
        state[y + height - 1][x + width] = getNotation();
        board[y][x + width] = getInfo();
        board[y + height - 1][x + width] = getInfo();
        x++;
    }

    /**
     * Method that moves the piece up, if it is possible
     */
    public void moveUp() {
        if (!up()) return; // Check if possible move
        // Make move up and actualize board
        state[y + height - 1][x] = 0;
        state[y + height - 1][x + width - 1] = 0;
        board[y + height - 1][x] = -1;
        board[y + height - 1][x + width - 1] = -1;
        state[y - 1][x] = getNotation();
        state[y - 1][x + width - 1] = getNotation();
        board[y - 1][x] = getInfo();
        board[y - 1][x + width - 1] = getInfo();
        y--;
    }

    /**
     * Method that moves the piece down, if it is possible
     */
    public void moveDown() {
        if (!down()) return; // Check if possible move
        // Make move down and actualize board
        state[y][x] = 0;
        state[y][x + width - 1] = 0;
        board[y][x] = -1;
        board[y][x + width - 1] = -1;
        state[y + height][x] = getNotation();
        state[y + height][x + width - 1] = getNotation();
        board[y + height][x] = getInfo();
        board[y + height][x + width - 1] = getInfo();
        y++;
    }
}
