package ui;

import core.StyledButton;
import core.StyledLabel;
import io.IOUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static main.Constants.*;

/**
 * Defines the ui for the dashboard class
 */
public class DashboardComponent extends JPanel {

    /**
     * Constructor method that initializes the data needed to show the component correctly
     *
     * @param buttons the list of all the buttons needed in the component
     * @param labels the list of all the labels needed in the component
     */
    public DashboardComponent(ArrayList<StyledButton> buttons, ArrayList<StyledLabel> labels) {

        setPreferredSize(new Dimension(TITLE_SIZE * 3, TITLE_SIZE * ROWS));
        setBackground(COLOR_BACKGROUND);
        setLayout(new BorderLayout());

        int borderSize = TITLE_SIZE / 8;
        setBorder(new EmptyBorder(new Insets(borderSize, borderSize, 0, borderSize)));


        Font labelFont;
        try {
            labelFont = IOUtils.readFromTtf("DotGothic.ttf");
        } catch (IOException | FontFormatException e) {
            System.out.println("Error opening font file: " + e.getMessage());
            return;
        }

        JLabel title = new JLabel(GAME_NAME);
        title.setBackground(COLOR_BACKGROUND);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(labelFont.deriveFont(Font.BOLD, (float) TITLE_SIZE / 3));

        JPanel gridWrapper = new JPanel(new GridBagLayout());
        gridWrapper.setBackground(COLOR_BACKGROUND);

        GridBagConstraints constraints = new GridBagConstraints();

        // padding settings to put some space between the buttons
        constraints.insets = new Insets(0, TITLE_SIZE / 8, TITLE_SIZE / 8, TITLE_SIZE / 8);

        for (StyledButton button : buttons) {

            constraints.gridx = button.getPos().x;
            constraints.gridy = button.getPos().y;

            gridWrapper.add(button, constraints);
        }

        constraints.insets = new Insets(0, 0, 0, 0);

        for (StyledLabel label : labels) {

            constraints.gridx = label.getPos().x;
            constraints.gridy = label.getPos().y;

            gridWrapper.add(label, constraints);
        }

        add(title, BorderLayout.NORTH);
        add(gridWrapper, BorderLayout.CENTER);
    }
}
