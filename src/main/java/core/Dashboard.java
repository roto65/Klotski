package core;

import com.google.gson.JsonSyntaxException;
import com.mongodb.MongoException;
import core.listener.MovePerformedListener;
import core.listener.PostGameActionsListener;
import io.GsonFileParser;
import io.JsonFileChooser;
import io.db.MongoDbConnection;
import io.db.schemas.LevelSchema;
import solver.Solver;
import ui.DashboardComponent;
import ui.dialogs.DbErrorDialog;
import ui.dialogs.LevelSelectorDialog;
import ui.dialogs.PostGameDialog;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static main.Constants.USE_DB_HINT_CASHING;

public class Dashboard  implements MovePerformedListener, PostGameActionsListener {

    private final Board board;
    private final DashboardComponent dashboardComponent;

    private StyledLabel moveCounter;
    private StyledLabel levelLabel;

    private ArrayList<StyledButton> buttons;
    private ArrayList<StyledLabel> labels;

    public Dashboard(Board board) {
        this.board = board;

        loadButtons();
        loadLabels();

        board.setMoveCountIncrementListener(this);

        dashboardComponent = new DashboardComponent(buttons, labels);
    }

    public DashboardComponent getDashboardComponent() {
        return dashboardComponent;
    }

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

    private void loadLabels() {
        this.labels = new ArrayList<>();

        levelLabel = new StyledLabel("Level: ", "1", new Point(1, 4));
        labels.add(levelLabel);

        moveCounter = new StyledLabel("Moves: ", "0", new Point(0, 4));
        labels.add(moveCounter);
    }

    @Override
    public void triggerPostGame() {
        int moves = Integer.parseInt(moveCounter.getVariableText());

        new PostGameDialog(moves, board.getMinimumMoves(), this).showDialog();
    }

    @Override
    public void incrementMoveCounter() {
        int currCount = Integer.parseInt(moveCounter.getVariableText());
        moveCounter.setVariableText(String.valueOf(++currCount));
    }

    private void decrementMoveCounter() {
        int currCount = Integer.parseInt(moveCounter.getVariableText());

        if (currCount != 0) {
            moveCounter.setVariableText(String.valueOf(--currCount));
        }
    }

    private void setMoveCounter(int moves) {
        moveCounter.setVariableText(String.valueOf(moves));
    }

    private void resetMoveCounter() {
        moveCounter.setVariableText(String.valueOf(0));
    }

    private void setLevelLabel(int levelNumber) {
        levelLabel.setVariableText(String.valueOf(levelNumber));
    }

    @Override
    public void reset() {
        board.resetBoard();
        resetMoveCounter();
    }

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

    @Override
    public boolean loadLevelFromFile() {
        JsonFileChooser fileChooser = new JsonFileChooser();
        File file = fileChooser.showLoadDialog();

        if (file == null) return false;

        GsonFileParser parser = new GsonFileParser(file.getAbsolutePath());

        try {
            LevelSchema newLevel = parser.load(true);
            board.resetBoard(newLevel);

            setMoveCounter(newLevel.getIteratorIndex());
            setLevelLabel(newLevel.getLevelNumber());
        } catch (JsonSyntaxException | NullPointerException e) {
            System.out.println("Error loading Json save file");
            return false;
        }
        return true;
    }

    private void saveLevel() {
        JsonFileChooser fileChooser = new JsonFileChooser();
        File file = fileChooser.showSaveDialog();

        if (file == null) return;

        GsonFileParser parser = new GsonFileParser(file.getAbsolutePath());

        parser.save(board.getCurrentLevel());
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
