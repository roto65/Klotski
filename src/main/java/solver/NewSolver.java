package solver;

import core.HintSchema;
import core.Move;
import io.db.MongoDbConnection;
import ui.blocks.Block;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

import static main.Constants.*;

public class NewSolver {

    static int[][] state; // Keep  the current state of the board based on pieces
    static int[][] board; // Keep  the current full state of the board
    static Map<String, Boolean> m; // Keep track of visited states
    static Map<String, Integer> depth; // Keep track of the depth of exploration
    static Map<String, Integer> st; // Map between states and integers
    static Map<Integer, String> ts; // Inverse map of the above-mentioned
    static Map<Integer, String> full; // From integers position to full board state
    static int[] parent; // Keep track of the parents in exploration
    static Queue<String> q; // Queue to keep current visited state
    static int c; // Number of states visited so far
    static String code;
    static List<Piece> pieces; // Array of the different pieces to locate in board
    static List<char []> boards;
    static List<String> states;

    private static void init(ArrayList<Block> blocks) {
        state = new int[ROWS][COLUMNS];
        board = new int[ROWS][COLUMNS];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = 0;
                board[i][j] = -1;
            }
        }

        m = new HashMap<>();
        depth = new HashMap<>();
        st = new HashMap<>();
        ts = new HashMap<>();
        full = new HashMap<>();

        parent = new int[30000];

        q = new LinkedList<>();

        c = 0;

        pieces = new ArrayList<>();
        boards = new ArrayList<>();
        states = new ArrayList<>();

        for (int i = 0; i < blocks.size(); i++) {
            pieces.add(new Piece(blocks.get(i), i));
        }
    }

    static void encode() {
        // This method encodes the state of the board in a string
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                builder.append((char) ('0' + state[i][j]));
                builder.append(board[i][j] < 0 ? '0' : (char) ('0' + board[i][j]));
            }
        code = builder.toString();
    }

    static String getCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length(); i += 2) builder.append(code.charAt(i));
        return builder.toString();
    }

    static void setBoard(String s) {
        // This method reconstructs a board base on it's encoding
        StringBuilder s_ = new StringBuilder();
        for (int i = 0; i < s.length(); i += 2) s_.append(s.charAt(i));

        // Empty cells of the board
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = 0;
                board[i][j] = -1;
            }
        }
        // Fill the board with pieces positions
        int p = 0;
        int c = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                switch (s_.charAt(c)) {
                    case '1' -> {
                        pieces.set(p, new Piece(i, j, 1, 1, (s.charAt(2 * c + 1) - '0')));
                    }
                    case '2' -> {
                        pieces.set(p, new Piece(i, j, 1, 2, (s.charAt(2 * c + 1) - '0')));
                        s_.setCharAt(c + 4, '*');
                    }
                    case '3' -> {
                        pieces.set(p, new Piece(i, j, 2, 1, (s.charAt(2 * c + 1) - '0')));
                        s_.setCharAt(c + 1, '*');
                    }
                    case '4' -> {
                        pieces.set(p, new Piece(i, j, 2, 2, (s.charAt(2 * c + 1) - '0')));
                        s_.setCharAt(c + 1, '*');
                        s_.setCharAt(c + 4, '*');
                        s_.setCharAt(c + 5, '*');
                    }
                    default -> p--;
                }
                p++;
                c++;
            }
        }
    }

    static boolean checkEnd() {
        // Check if final state ( solution board ) has been reached
        return (state[3][1] == state[3][2] && state[3][2] == state[4][1] &&
                state[4][1] == state[4][2] && state[4][1] == 4);
    }

    static void update(String aux, String cur) {
        q.add(aux);
        m.put(aux, true);
        depth.put(aux, depth.get(cur) + 1);
        ts.put(c, aux);
        full.put(c, code);
        st.put(aux, c++);
        parent[st.get(aux)] = st.get(cur);
    }

    static void backtrackSolution(String s) {
        // Recursively printing optimal path
        if (st.get(s) == 0) {
            setBoard(full.get(st.get(s)));
            saveState();
            return;
        }
        backtrackSolution(ts.get(parent[st.get(s)]));
        setBoard(full.get(st.get(s)));
        saveState();
    }

    static void saveState() {
        StringBuilder boardBuilder = new StringBuilder();
        StringBuilder stateBuilder = new StringBuilder();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                boardBuilder.append((board[i][j] >= 0 ? (char) (board[i][j] + 'A') : ' '));
                stateBuilder.append(state[i][j]);
            }
        }
        boards.add(boardBuilder.toString().toCharArray());
        states.add(stateBuilder.toString());
    }

    static boolean moveSpecific(Piece p, String cur, int direction) {
        if (direction == 0 && p.left()) p.moveLeft();
        else if (direction == 1 && p.right()) p.moveRight();
        else if (direction == 2 && p.up()) p.moveUp();
        else if (direction == 3 && p.down()) p.moveDown();
        else return false;

        encode();
        String aux = getCode();

        m.putIfAbsent(aux, false);

        if (!m.get(aux)) {
            update(aux, cur);
            // Check if reached final state
            if (checkEnd()) {
                backtrackSolution(aux);
                return true;
            }
            if (moveSpecific(p, cur, 0)) return true;
            if (moveSpecific(p, cur, 1)) return true;
            if (moveSpecific(p, cur, 2)) return true;
            if (moveSpecific(p, cur, 3)) return true;
        }

        switch (direction) {
            case 0 -> p.moveRight();
            case 1 -> p.moveLeft();
            case 2 -> p.moveDown();
            case 3 -> p.moveUp();
        }
        return false;
    }

    public static Move start(ArrayList<Block> blocks) {
        // Initialize Board
        init(blocks);

        evalMoves();

        if (USE_DB_HINT_CASHING) {
            uploadMoves();
            return null;
        } else {
            return findMove(boards.get(0), boards.get(1));
        }
    }

    public static void uploadMoves() {
        MongoDbConnection dbConnection = new MongoDbConnection();

        List<HintSchema> movesToUpload = new ArrayList<>();

        for (int i = 0; i < boards.size() - 1; i++) {
            HintSchema hint = new HintSchema();

            hint.setState(states.get(i));
            hint.setBestMove(findMove(boards.get(i), boards.get(i + 1)));

            movesToUpload.add(hint);
        }
        dbConnection.uploadHints(movesToUpload);
    }

    public static String getState(ArrayList<Block> blocks) {
        init(blocks);

        StringBuilder builder = new StringBuilder();

        for (int[] row : state) {
            for (int cell : row) {
                builder.append(cell);
            }
        }
        return builder.toString();
    }

    public static void evalMoves() {
        // Start BFS
        encode();
        String s = getCode();
        q.add(s);
        m.put(s, true);
        depth.put(s, 0);
        ts.put(c, s);
        full.put(c, code);
        st.put(s, c++);
        parent[0] = 0;

        while (!q.isEmpty()) {
            String cur = q.poll();
            // Reconstruct board
            setBoard(full.get(st.get(cur)));
            encode();
            // Check if any piece can be moved
            for (int i = 0; i < 10; i++) {
                if (moveSpecific(pieces.get(i), cur, 0)) return;
                if (moveSpecific(pieces.get(i), cur, 1)) return;
                if (moveSpecific(pieces.get(i), cur, 2)) return;
                if (moveSpecific(pieces.get(i), cur, 3)) return;
            }
        }
    }

    public static Move findMove(char [] prev, char [] curr) {
        int fromIndex = 0, toIndex = 0;
        char blockMoved = 'z';

        for (int i = 0; i < prev.length; i++) {
            if (prev[i] == ' ' && prev[i] != curr[i]) {
                toIndex = i;
            }
            if (curr[i] == ' ' && prev[i] != curr[i]) {
                fromIndex = i;
                blockMoved = prev[i];
            }
        }

        int occurrences = 0;
        for (char c : curr) {
            if (c == blockMoved) occurrences++;
        }

        if (occurrences > 1) {
            if (fromIndex - toIndex > 4) {
                toIndex += 4;
            } else if (toIndex - fromIndex > 4) {
              toIndex -= 4;
            } else if (fromIndex - toIndex > 1) {
                toIndex++;
            } else if (toIndex - fromIndex > 1) {
                toIndex--;
            }
        }

        if (USE_SOLVER_DEBUG_PRINT) {

            for (int i = 0; i < prev.length; i++) {
                if (i % 4 == 3) {
                    System.out.println(prev[i]);
                } else {
                    System.out.print(prev[i] + "|");
                }
            }

            System.out.println();

            for (int i = 0; i < curr.length; i++) {
                if (i % 4 == 3) {
                    System.out.println(curr[i]);
                } else {
                    System.out.print(curr[i] + "|");
                }
            }

            System.out.println("\nFrom: " + fromIndex + " to: " + toIndex + "\n");
        }

        return new Move(fromIndex, toIndex);
    }

    static class Piece {
        int x;
        int y;
        int width;
        int height;
        int info;

        Piece(int y, int x, int width, int height, int info) {
            this.width = width;
            this.height = height;
            this.info = info;

            setYX(y, x);
        }

        Piece(Block block, int info) {
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
}
