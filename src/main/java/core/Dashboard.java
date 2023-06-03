package core;

import core.listener.MoveCountIncrementListener;
import io.db.MongoDbConnection;
import solver.NewSolver;
import ui.DashboardComponent;

import java.awt.*;
import java.util.ArrayList;

import static main.Constants.USE_DB_HINT_CASHING;

public class Dashboard  implements MoveCountIncrementListener {

    private final Board board;
    private final DashboardComponent dashboardComponent;

    private StyledLabel moveCounter;
    private StyledLabel layoutLabel;

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

    private void loadButtons() {
        this.buttons = new ArrayList<>();

        StyledButton resetButton = new StyledButton("New Game", new Point(0, 0));
        resetButton.addActionListener(e -> {
            board.resetBoard();
            setMoveCounter(0);
        });
        buttons.add(resetButton);

        StyledButton levelButton = new StyledButton("Levels", new Point(1, 0));
        resetButton.addActionListener(e -> {
            board.resetBoard();
        });
        buttons.add(levelButton);

        StyledButton saveButton = new StyledButton("Save", new Point(0, 1));
        saveButton.addActionListener(e -> {
            board.save();
        });
        buttons.add(saveButton);

        StyledButton loadButton = new StyledButton("Load", new Point(1, 1));
        loadButton.addActionListener(e -> {
            board.load();
        });
        buttons.add(loadButton);

        StyledButton undoButton = new StyledButton("Undo", new Point(0, 2));
        undoButton.addActionListener(e -> {
            if (board.undo() && !board.checkWin())
                decrementMoveCounter();
        });
        buttons.add(undoButton);

        StyledButton redoButton = new StyledButton("Redo", new Point(1, 2));
        redoButton.addActionListener(e -> {
            if (board.redo() && !board.checkWin())
                incrementMoveCounter();
        });
        buttons.add(redoButton);

        StyledButton hintButton = new StyledButton("Hint", new Point(0, 3));
        hintButton.addActionListener(e -> {
            if (!board.checkWin()) {
                getHint();
                incrementMoveCounter();
            }
        });
        buttons.add(hintButton);

        StyledButton exitButton = new StyledButton("Exit", new Point(1, 3));
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        buttons.add(exitButton);
    }

    private void loadLabels() {

        layoutLabel = new StyledLabel("Level: ", "000", new Point(1, 4));
        this.labels = new ArrayList<>();

        moveCounter = new StyledLabel("Moves: ", "0", new Point(0, 4));
        labels.add(moveCounter);
        labels.add(layoutLabel);
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

    private void setMoveCounter(int count) {
        moveCounter.setVariableText(String.valueOf(count));
    }

    private void getHint() {

        if (USE_DB_HINT_CASHING) {
            MongoDbConnection dbConnection = new MongoDbConnection();

            Move move = dbConnection.findHint(NewSolver.getState(board.getBlocks()));

            if (move == null) {
                NewSolver.start(board.getBlocks());
                move = dbConnection.findHint(NewSolver.getState(board.getBlocks()));
            }
            board.performMoveUnchecked(move);
        } else {
            Move move = NewSolver.start(board.getBlocks());

            assert move != null;
            board.performMoveUnchecked(move);

            if (move.isCut()) {
                incrementMoveCounter();
            }
        }
        board.checkWin();
    }
}
