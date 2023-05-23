package ui;

import core.StyledButton;
import core.StyledLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.Constants.ROWS;
import static main.Constants.TITLE_SIZE;

public class DashboardComponent extends JPanel {

    private GridBagConstraints constraints;

    public DashboardComponent(ArrayList<StyledButton> buttons, ArrayList<StyledLabel> labels) {

        setPreferredSize(new Dimension(TITLE_SIZE * 3, TITLE_SIZE * ROWS));

        setBackground(new Color(255,91,46));

        this.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        // padding settings to put some space between the buttons
        constraints.insets = new Insets(TITLE_SIZE / 16, TITLE_SIZE / 8, TITLE_SIZE / 16, TITLE_SIZE / 8);

        for (StyledButton button : buttons) {

            constraints.gridx = button.getPos().x;
            constraints.gridy = button.getPos().y;

            add(button, constraints);
        }

        constraints.insets = new Insets(TITLE_SIZE / 16, 0, 0, 0);

        for (StyledLabel label : labels) {

            constraints.gridx = label.getPos().x;
            constraints.gridy = label.getPos().y;

            add(label, constraints);
        }

        /* Point moveCounterPos = moveCounter.getPos();

        constraints.gridwidth = 2;

        constraints.gridx = moveCounterPos.x;
        constraints.gridy = moveCounterPos.y;

        add(moveCounter, constraints);*/
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
