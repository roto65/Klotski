package ui.dialogs;

import core.StyledButton;
import core.listener.PostGameActionsListener;
import io.IOUtils;
import ui.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static main.Constants.*;

/**
 * Defines a dialog window used when the player wins the game
 */
public class PostGameDialog extends JFrame {

    /**
     * The number of moves the player made to win
     */
    private final int moves;

    /**
     * The minimum number of moves needed to win the current level
     */
    private final int par;

    /**
     * The layout portion that contains all the buttons
     */
    private JPanel buttonWrapperPanel;

    /**
     * The big text that is displayed
     */
    private JLabel labelTitle;

    /**
     * The smaller text that is displayed under the other one
     */
    private JLabel labelSubTitle;

    /**
     * The listener needed to send back to the dashboard all the actions of the buttons
     */
    private final PostGameActionsListener listener;

    /**
     * Constructor method that initializes the dialog's data
     *
     * @param moves the number of moves the player made
     * @param par the minimum number of moves needed to win the current level
     * @param listener the listener needed to sent back to the dashboard all the actions of the buttons
     */
    public PostGameDialog(int moves, int par, PostGameActionsListener listener) {
        this.moves = moves;
        this.par = par;
        this.listener = listener;
    }

    /**
     * Method that defines dialog properties and widgets, including all the layouts. Then it shows it to the user
     */
    public void showDialog() {
        JDialog dialog = new JDialog(this, "You Won!", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(COLOR_BACKGROUND);
        wrapperPanel.setPreferredSize(new Dimension(TITLE_SIZE * 5, TITLE_SIZE * 2));

        int borderSize = TITLE_SIZE / 8;
        wrapperPanel.setBorder(new EmptyBorder(new Insets(borderSize, borderSize, 0, borderSize)));

        initializeLabels();

        buttonWrapperPanel = new JPanel(new FlowLayout());
        buttonWrapperPanel.setBackground(COLOR_BACKGROUND);

        populateButtons();

        wrapperPanel.add(labelTitle, BorderLayout.NORTH);
        wrapperPanel.add(labelSubTitle, BorderLayout.CENTER);
        wrapperPanel.add(buttonWrapperPanel, BorderLayout.SOUTH);

        dialog.add(wrapperPanel);

        dialog.setIconImage(Window.getWindowIcon());

        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /**
     * Method that initializes the labels used in this dialog
     */
    private void initializeLabels() {
        Font labelFont;
        try {
            labelFont = IOUtils.readFromTtf("DotGothic.ttf");
        } catch (IOException | FontFormatException e) {
            System.out.println("Error opening font file: " + e.getMessage());
            return;
        }

        labelTitle = new JLabel("You won in " + moves + " moves!");
        labelTitle.setBackground(COLOR_BACKGROUND);
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(labelFont.deriveFont(Font.BOLD, (float) TITLE_SIZE / 4));

        labelSubTitle = new JLabel();
        labelSubTitle.setBackground(COLOR_BACKGROUND);
        labelSubTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelSubTitle.setForeground(Color.WHITE);
        labelSubTitle.setFont(labelFont.deriveFont(Font.BOLD, (float) TITLE_SIZE / 6));

        int deltaMoves = moves - par;
        if (deltaMoves > 0) {
            if (USE_SHAME_BRAKETS) {
                labelSubTitle.setText("This level can be solved in " + par + " moves (+" + deltaMoves + ")");
            } else {
                labelSubTitle.setText("This level can be solved in " + par + " moves");
            }
        } else if (deltaMoves == 0) {
            labelSubTitle.setText("You made the optimal amount of moves");
        } else {
            labelSubTitle.setText("You are too good at this game, did you cheat?");
        }
    }

    /**
     * Method that initializes all the buttons needed in this dialog
     */
    private void populateButtons() {
        JButton newGameButton = new StyledButton("New Game");
        newGameButton.addActionListener(e -> {
            listener.reset();
            dispose();
        });

        JButton levelsButton = new StyledButton("Levels");
        levelsButton.addActionListener(e -> {
            if (listener.loadLevel())
                dispose();
        });

        JButton loadButton = new StyledButton("Load");
        loadButton.addActionListener(e -> {
            if (listener.loadLevelFromFile())
                dispose();
        });

        JButton exitButton = new StyledButton("Exit");
        exitButton.addActionListener(e -> {
            listener.exit();
            dispose();
        });

        buttonWrapperPanel.add(newGameButton);
        buttonWrapperPanel.add(levelsButton);
        buttonWrapperPanel.add(loadButton);
        buttonWrapperPanel.add(exitButton);
    }
}
