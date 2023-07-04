package core;

import com.google.gson.JsonSyntaxException;
import com.mongodb.MongoException;
import core.listener.MovePerformedListener;
import core.listener.PostGameActionsListener;
import io.GsonFileParser;
import io.JsonFileChooser;
import io.db.MongoDbConnection;
import io.schemas.LevelSchema;
import solver.Solver;
import ui.DashboardComponent;
import ui.dialogs.DbErrorDialog;
import ui.dialogs.LevelSelectorDialog;
import ui.dialogs.PostGameDialog;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static main.Constants.USE_DB_HINT_CASHING;

/**
 * Defines all the widgets that the player can interact with
 */
public class Dashboard  implements MovePerformedListener, PostGameActionsListener {

    /**
     * The board object associated with this instance
     */
    private final Board board;

    /**
     * Objects that represents the GUI part of the Dashboard itself
     *
     * @see DashboardComponent
     */
    private final DashboardComponent dashboardComponent;

    /**
     * The label used as a move counter
     */
    private StyledLabel moveCounter;

    /**
     * The label used as a level number indicator
     */
    private StyledLabel levelLabel;

    /**
     * The container used to store all the dashboard's buttons
     */
    private ArrayList<StyledButton> buttons;

    /**
     * The container used to store all the dashboard's labels
     */
    private ArrayList<StyledLabel> labels;

    /**
     * Constructor method that initializes all the widgets in the dashboard
     *
     * @param board the board instance controlled by the dashboard
     */
    public Dashboard(Board board) {
        this.board = board;

        loadButtons();
        loadLabels();

        board.setMoveCountIncrementListener(this);

        dashboardComponent = new DashboardComponent(buttons, labels);
    }

    /**
     * @return ui component bound to this dashboard instance
     */
    public DashboardComponent getDashboardComponent() {
        return dashboardComponent;
    }

    /**
     * Method that instantiates all the buttons and stores inside their list
     */
    @SuppressWarnings("CodeBlock2Expr")
    private void loadButtons() {
        this.buttons = new ArrayList<>();

        StyledButton resetButton = new StyledButton("New Game", new Point(0, 0));
        resetButton.addActionListener(e -> {
            reset();
        });
        buttons.add(resetButton);

        StyledButton levelButton = new StyledButton("Levels", new Point(1, 0));
        levelButton.addActionListener(e -> {
            loadLevel();
        });
        buttons.add(levelButton);

        StyledButton saveButton = new StyledButton("Save", new Point(0, 1));
        saveButton.addActionListener(e -> {
            if (!board.isGameWon())
                saveLevel();
        });
        buttons.add(saveButton);

        StyledButton loadButton = new StyledButton("Load", new Point(1, 1));
        loadButton.addActionListener(e -> {
            loadLevelFromFile();
        });
        buttons.add(loadButton);

        StyledButton undoButton = new StyledButton("Undo", new Point(0, 2));
        undoButton.addActionListener(e -> {
            if (!board.isGameWon())
                if (board.undo()) decrementMoveCounter();
        });
        buttons.add(undoButton);

        StyledButton redoButton = new StyledButton("Redo", new Point(1, 2));
        redoButton.addActionListener(e -> {
            if (!board.isGameWon())
                if (board.redo()) incrementMoveCounter();
        });
        buttons.add(redoButton);

        StyledButton hintButton = new StyledButton("Hint", new Point(0, 3));
        hintButton.addActionListener(e -> {
            if (!board.isGameWon()) {
                getHint();
            }
        });
        buttons.add(hintButton);

        StyledButton exitButton = new StyledButton("Exit", new Point(1, 3));
        exitButton.addActionListener(e -> {
            exit();
        });
        buttons.add(exitButton);
    }

    /**
     * Method that instantiates all the labels and stores them inside their list
     */
    private void loadLabels() {
        this.labels = new ArrayList<>();

        levelLabel = new StyledLabel("Level: ", "1", new Point(1, 4));
        labels.add(levelLabel);

        moveCounter = new StyledLabel("Moves: ", "0", new Point(0, 4));
        labels.add(moveCounter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerPostGame() {
        int moves = Integer.parseInt(moveCounter.getVariableText());

        new PostGameDialog(moves, board.getMinimumMoves(), this).showDialog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementMoveCounter() {
        int currCount = Integer.parseInt(moveCounter.getVariableText());
        moveCounter.setVariableText(String.valueOf(++currCount));
    }

    /**
     * This method removes 1 form the current move count
     */
    public void decrementMoveCounter() {
        int currCount = Integer.parseInt(moveCounter.getVariableText());

        if (currCount != 0) {
            moveCounter.setVariableText(String.valueOf(--currCount));
        }
    }

    /**
     * This method updates the move counter to the desired value
     *
     * @param moves the number that replaces the current count
     */
    private void setMoveCounter(int moves) {
        moveCounter.setVariableText(String.valueOf(moves));
    }

    /**
     * This method sets the move counter back to 0
     */
    public void resetMoveCounter() {
        moveCounter.setVariableText(String.valueOf(0));
    }

    /**
     * This method updates the level label to the desired value
     *
     * @param levelNumber the level number that replaces the current level number
     */
    private void setLevelLabel(int levelNumber) {
        levelLabel.setVariableText(String.valueOf(levelNumber));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        board.resetBoard();
        resetMoveCounter();
    }

    /**
     * This method is called when the player wants a new hint
     */
    private void getHint() {
        incrementMoveCounter();

        if (USE_DB_HINT_CASHING) {
            MongoDbConnection dbConnection;
            try {
                dbConnection = new MongoDbConnection();
            } catch (MongoException e) {
                new DbErrorDialog(e.getMessage()).showDialog();
                return;
            }

            Move move = dbConnection.findHint(Solver.getState(board.getBlocks()));

            if (move == null) {
                Solver.start(board.getBlocks());
                move = dbConnection.findHint(Solver.getState(board.getBlocks()));
            }
            board.performMoveUnchecked(move);

            dbConnection.closeClient();
        } else {
            Move move = Solver.start(board.getBlocks());

            assert move != null;
            board.performMoveUnchecked(move);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loadLevel() {

        MongoDbConnection db;
        try {
            db = new MongoDbConnection();
        } catch (MongoException e) {
            new DbErrorDialog(e.getMessage()).showDialog();
            return false;
        }

        LevelSelectorDialog selector = new LevelSelectorDialog();
        selector.showDialog();
        int selectedLevel = selector.getSelectedLevel();

        if (selectedLevel == -1) return false;

        resetMoveCounter();
        setLevelLabel(selectedLevel);

        board.resetBoard(db.getLevel(selectedLevel));

        db.closeClient();

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loadLevelFromFile() {
        JsonFileChooser fileChooser = new JsonFileChooser();
        File file = fileChooser.showLoadDialog();

        if (file == null) return false;

        GsonFileParser parser = new GsonFileParser(file.getAbsolutePath());

        try {
            LevelSchema newLevel = parser.load(true);
            board.loadBoard(newLevel);

            setMoveCounter(newLevel.getIteratorIndex());
            setLevelLabel(newLevel.getLevelNumber());
        } catch (JsonSyntaxException | NullPointerException e) {
            System.out.println("Error loading Json save file");
            return false;
        }
        return true;
    }

    /**
     * This method is called when the player wants to save the current game to a file on the pc
     */
    private void saveLevel() {
        JsonFileChooser fileChooser = new JsonFileChooser();
        File file = fileChooser.showSaveDialog();

        if (file == null) return;

        GsonFileParser parser = new GsonFileParser(file.getAbsolutePath());

        parser.save(board.getCurrentLevel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        System.exit(0);
    }

    /**
     * @return the move counter object
     */
    public StyledLabel getMoveCounter (){
        return moveCounter;
    }

    /**
     * @return the level label object
     */
    public StyledLabel getLevelLabel (){
        return levelLabel;
    }
}
