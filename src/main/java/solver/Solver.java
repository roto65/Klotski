package solver;

import com.mongodb.MongoException;
import core.Move;
import io.db.MongoDbConnection;
import io.schemas.HintSchema;
import ui.blocks.Block;
import ui.dialogs.DbErrorDialog;

import java.util.*;

import static main.Constants.*;

/**
 * Provides an algorithm to evaluate the best move for any given board state
 */
public class Solver {

    /**
     * Keeps the current state of the board based on pieces
     */
    public static int[][] state;

    /**
     * Keeps the current full state of the board
     */
    public static int[][] board;

    /**
     * Keeps track of visited states
     */
    private static Map<String, Boolean> m;

    /**
     * Keeps track of the depth of exploration
     */
    private static Map<String, Integer> depth;

    /**
     * Map between states and integers
     */
    private static Map<String, Integer> st;

    /**
     * Inverse map of the above-mentioned
     */
    private static Map<Integer, String> ts;

    /**
     * From integers position to full board state
     */
    private static Map<Integer, String> full;

    /**
     * Keep track of the parents in exploration
     */
    private static ArrayList<Integer> parent;

    /**
     * Queue to keep current visited state
     */
    private static Queue<String> q;

    /**
     * Number of states visited so far
     */
    private static int c;

    /**
     * Temporary board representation
     */
    private static String code;

    /**
     * List of the different pieces to locate in board
     */
    private static List<SolverPiece> pieces;

    /**
     * Boards visited between current and solved
     */
    private static List<char []> boards;

    /**
     * States visited between current and solved
     */
    private static List<String> states;

    /**
     * Method that initializes all the above data structures
     *
     * @param blocks the current board blocks
     */
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

        parent = new ArrayList<>(1);

        q = new LinkedList<>();

        c = 0;

        pieces = new ArrayList<>();
        boards = new ArrayList<>();
        states = new ArrayList<>();

        for (int i = 0; i < blocks.size(); i++) {
            pieces.add(new SolverPiece(blocks.get(i), i));
        }
    }

    /**
     * Method that encodes the state of the board in a string
     */
    private static void encode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++) {
                builder.append((char) ('0' + state[i][j]));
                builder.append(board[i][j] < 0 ? '0' : (char) ('0' + board[i][j]));
            }
        code = builder.toString();
    }

    /**
     * @return code of the board based on current information
     */
    private static String getCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length(); i += 2) builder.append(code.charAt(i));
        return builder.toString();
    }


    /**
     * Method that reconstructs a board base on it's encoding
     */
    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    private static void setBoard(String s) {
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
                        pieces.set(p, new SolverPiece(i, j, 1, 1, (s.charAt(2 * c + 1) - '0')));
                    }
                    case '2' -> {
                        pieces.set(p, new SolverPiece(i, j, 1, 2, (s.charAt(2 * c + 1) - '0')));
                        s_.setCharAt(c + 4, '*');
                    }
                    case '3' -> {
                        pieces.set(p, new SolverPiece(i, j, 2, 1, (s.charAt(2 * c + 1) - '0')));
                        s_.setCharAt(c + 1, '*');
                    }
                    case '4' -> {
                        pieces.set(p, new SolverPiece(i, j, 2, 2, (s.charAt(2 * c + 1) - '0')));
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

    /**
     * Method that checks if the final state has been reached
     *
     * @return true if the condition is met
     */
    private static boolean checkEnd() {
        return (state[3][1] == state[3][2] && state[3][2] == state[4][1] &&
                state[4][1] == state[4][2] && state[4][1] == 4);
    }

    /**
     * Method that updates all the data structures when a new state is inserted
     *
     * @param aux new state to be inserted
     * @param cur current state
     */
    private static void update(String aux, String cur) {
        q.add(aux);
        m.put(aux, true);
        depth.put(aux, depth.get(cur) + 1);
        ts.put(c, aux);
        full.put(c, code);
        st.put(aux, c++);
        try {
            parent.set(st.get(aux), st.get(cur));
        } catch (IndexOutOfBoundsException e) {
            parent.add(st.get(aux), st.get(cur));
        }
    }

    /**
     * This method recursively evaluates the optimal path for the solution
     *
     * @param s current state
     */
    private static void backtrackSolution(String s) {
        if (st.get(s) == 0) {
            setBoard(full.get(st.get(s)));
            saveState();
            return;
        }
        backtrackSolution(ts.get(parent.get(st.get(s))));
        setBoard(full.get(st.get(s)));
        saveState();
    }

    /**
     * Method that saves a new state to the boards/states lists
     */
    private static void saveState() {
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

    /**
     * Method that moves a piece in every possible way
     *
     * @param p the piece that needs to be moved
     * @param cur the current state
     * @param direction the direction the piece is moving
     * @return true if the piece could be moved
     */
    private static boolean moveSpecific(SolverPiece p, String cur, int direction) {
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

    /**
     * Method that starts the algorithm
     *
     * @param blocks current block list of the board
     * @return the best move for the given state
     */
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

    /**
     * Method that opens a new database connection and saves all the middle states to the hint collection
     */
    private static void uploadMoves() {
        MongoDbConnection dbConnection;
        try {
            dbConnection = new MongoDbConnection();
        } catch (MongoException e) {
            new DbErrorDialog(e.getMessage()).showDialog();
            return;
        }

        List<HintSchema> movesToUpload = new ArrayList<>();

        for (int i = 0; i < boards.size() - 1; i++) {
            HintSchema hint = new HintSchema();

            hint.setState(states.get(i));
            hint.setBestMove(findMove(boards.get(i), boards.get(i + 1)));

            movesToUpload.add(hint);
        }
        dbConnection.uploadHints(movesToUpload);

        dbConnection.closeClient();
    }

    /**
     * Method that converts the given block list to a simpler string
     *
     * @param blocks list that needs to be converted
     * @return state representation as a string
     */
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

    /**
     * Method that handles the bfs algorithm
     */
    private static void evalMoves() {
        // Start BFS
        encode();
        String s = getCode();
        q.add(s);
        m.put(s, true);
        depth.put(s, 0);
        ts.put(c, s);
        full.put(c, code);
        st.put(s, c++);
        parent.add(0, 0);

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

    /**
     * Method that evaluates a Move done between two states
     *
     * @param prev first state
     * @param curr second state
     * @return evaluated move
     */
    private static Move findMove(char [] prev, char [] curr) {
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
            } else if (Math.abs(fromIndex - toIndex) == 4) {
                if (USE_SOLVER_DEBUG_PRINT) System.out.println("Nessuna modifica");
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
}
