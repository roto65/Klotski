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


public class PostGameDialog extends JFrame {

    private final int moves;
    private final int par;

    private JPanel buttonWrapperPanel;

    private JLabel labelTitle;
    private JLabel labelSubTitle;

    private final PostGameActionsListener listener;

    public PostGameDialog(int moves, int par, PostGameActionsListener listener) {
        this.moves = moves;
        this.par = par;
        this.listener = listener;
    }

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
