package core;

import ui.DashboardComponent;

import java.awt.*;
import java.util.ArrayList;

public class Dashboard  implements MoveCountIncrementListener{

    private final Board board;
    private final DashboardComponent dashboardComponent;

    private ArrayList<StyledButton> buttons;
    private StyledLabel moveCounter;

    public Dashboard(Board board) {
        this.board = board;

        loadButtons();

        moveCounter = new StyledLabel("Moves: ", "0", new Point(0, 4));

        board.setMoveCountIncrementListener(this);

        dashboardComponent = new DashboardComponent(buttons, moveCounter);
    }

    public DashboardComponent getDashboardComponent() {
        return dashboardComponent;
    }

    private void loadButtons() {
        this.buttons = new ArrayList<>();

        StyledButton resetButton = new StyledButton("New Game", new Point(0, 0));
        resetButton.addActionListener(e -> {
            board.resetBoard();
        });
        buttons.add(resetButton);

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
            board.undo();
        });
        buttons.add(undoButton);

        StyledButton redoButton = new StyledButton("Redo", new Point(1, 2));
        redoButton.addActionListener(e -> {
            board.redo();
        });
        buttons.add(redoButton);

        StyledButton hintButton = new StyledButton("Hint", new Point(0, 3));
        hintButton.addActionListener(e -> {
            // TODO: add this method
        });
        buttons.add(hintButton);

        StyledButton exitButton = new StyledButton("Exit", new Point(1, 3));
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        buttons.add(exitButton);
    }

    @Override
    public void incrementMoveCounter() {
        System.out.println(moveCounter.getVariableText());

        int count = 1 + Integer.parseInt(moveCounter.getVariableText());
        moveCounter.setVariableText(String.valueOf(count));

        dashboardComponent.repaint();
    }

    public void setMoveCounter(int count) {
        String text = moveCounter.getStaticText() + count;
        moveCounter.setText(text);

        dashboardComponent.repaint();
    }
}
