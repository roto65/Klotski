package ui;

import core.StyledButton;
import core.StyledLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.Constants.*;

public class DashboardComponent extends JPanel {

    public DashboardComponent(ArrayList<StyledButton> buttons, ArrayList<StyledLabel> labels) {

        setPreferredSize(new Dimension(TITLE_SIZE * 3, TITLE_SIZE * ROWS));

        setBackground(COLOR_BACKGROUND);

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

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

    }

}
