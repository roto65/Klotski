package ui;

import core.StyledButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.Constants.ROWS;
import static main.Constants.TITLE_SIZE;

public class DashboardComponent extends JPanel {

    private GridBagConstraints constraints;

    public DashboardComponent(ArrayList<StyledButton> buttons) {

        setPreferredSize(new Dimension(TITLE_SIZE * 3, TITLE_SIZE * ROWS));

        setBackground(new Color(255,91,46));

        this.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        // padding settings to put some space between the buttons
        constraints.insets = new Insets(0, TITLE_SIZE / 8, 0, TITLE_SIZE / 8);

        for (StyledButton button : buttons) {

            constraints.gridx = button.getPos().x;
            constraints.gridy = button.getPos().y;

            add(button, constraints);
        }

        JLabel moveCounter = new JLabel("Moves: 0");

        moveCounter.setPreferredSize(new Dimension(TITLE_SIZE, TITLE_SIZE / 2));

        constraints.gridx = 1;
        constraints.gridy = 4;

        add(moveCounter, constraints);
    }

    /*
    Buttons:
        - New Game (Reset)
        - Change load-out ?
        - Save
        - Load
        - Undo
        - Redo ?
        - Best Move (Hint)
        - Exit
    Move counter
     */

}
