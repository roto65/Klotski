package solver;

import ui.blocks.Block;

import java.awt.*;

import static main.Constants.COLUMNS;
import static main.Constants.ROWS;
import static solver.Solver.board;
import static solver.Solver.state;

public class SolverPiece {
    int x;
    int y;
    int width;
    int height;
    int info;

    SolverPiece(int y, int x, int width, int height, int info) {
        this.width = width;
        this.height = height;
        this.info = info;

        setYX(y, x);
    }

    SolverPiece(Block block, int info) {
        this.width = block.getBlockType().getWidth();
        this.height = block.getBlockType().getHeight();
        this.info = info;

        setYX(block.getPos());
    }

    int getInfo() {
        return info;
    }

    int getNotation() {
        /*
        Notation for pieces of the board
            width x height -> notation
            0x0 -> 0  ; 1x1 -> 1  ; 1x2 -> 2 ; 2x1 -> 3 ; 2x2 -> 4
        */
        if (width == 1 && height == 1) return 1;
        if (width == 1 && height == 2) return 2;
        if (width == 2 && height == 1) return 3;
        if (width == 2 && height == 2) return 4;
        return 0;
    }

    void setYX(Point pos) {
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

    void setYX(int y, int x) {
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

    boolean left() {
        // Whether is possible to move the current piece to the left
        if (x == 0) return false; // If it is touching the left border
        // If it has height 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y][x - 1] == 0 && state[y + height - 1][x - 1] == 0;
    }

    boolean right() {
        // Whether is possible to move the current piece to the right
        if (x + width == COLUMNS) return false; // If it is touching the right border
        // If it has height 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y][x + width] == 0 && state[y + height - 1][x + width] == 0;
    }

    boolean up() {
        // Whether is possible to move the current piece up.
        if (y == 0) return false; // If it is touching the top border
        // If it has width 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y - 1][x] == 0 && state[y - 1][x + width - 1] == 0;
    }

    boolean down() {
        // Whether is possible to move the current piece up.
        if (y + height == ROWS) return false; // If it is touching the bottom border
        // If it has width 1 or 2 blocks and there is a free space next to it
        // Otherwise it's not possible to make such move
        return state[y + height][x] == 0 && state[y + height][x + width - 1] == 0;
    }

    void moveLeft() {
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

    void moveRight() {
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

    void moveUp() {
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

    void moveDown() {
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