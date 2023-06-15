package ui;

import core.StyledButton;

import javax.swing.*;
import java.awt.*;

import static main.Constants.*;

public class LevelSelector extends JFrame {

    private int currentPage = 1;
    private int selectedLevel = -1;

    public LevelSelector() {
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void showLevelSelector() {
        JDialog levelSelectorFrame = new JDialog(this, "Level Selector", true);
        levelSelectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        levelSelectorFrame.setLayout(new BorderLayout());

        JPanel levelWrapperPanel = new JPanel(new CardLayout());

        JPanel pageButtonPanel = new JPanel(new FlowLayout());
        pageButtonPanel.setBackground(Color.GREEN);

        int buttonsPerPage = LEVEL_SELECTOR_GRID_ROWS * LEVEL_SELECTOR_GRID_COLUMNS;
        int totalPages = (int) Math.ceil((double) LEVELS / buttonsPerPage);

        JButton prevButton = new StyledButton("Prev", new Point(0, 0));
        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                ((CardLayout) levelWrapperPanel.getLayout()).show(levelWrapperPanel, String.valueOf(currentPage));
            }
        });
        pageButtonPanel.add(prevButton);

        JButton nextButton = new StyledButton("Next", new Point(0, 0));
        nextButton.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                ((CardLayout) levelWrapperPanel.getLayout()).show(levelWrapperPanel, String.valueOf(currentPage));
            }
        });
        pageButtonPanel.add(nextButton);

        int offset = 0;


        for (int j = 1; j <= totalPages; j++) {
            JPanel levelPanel = new JPanel(new GridLayout(LEVEL_SELECTOR_GRID_ROWS, LEVEL_SELECTOR_GRID_COLUMNS, 10, 10));

            levelPanel.setBackground(Color.MAGENTA);

            for (int i = 1; i <= buttonsPerPage; i++) {
                JButton levelButton = new StyledButton("Level " + (i + offset), new Point(0 ,0));
                final int levelNumber = (i + offset);
                levelButton.addActionListener(e -> {
                         selectedLevel = levelNumber;
                         levelSelectorFrame.dispose();
                });
                levelPanel.add(levelButton);
            }
            offset += buttonsPerPage;
            levelWrapperPanel.add(levelPanel, String.valueOf(j));
        }

        levelSelectorFrame.add(pageButtonPanel, BorderLayout.SOUTH);
        levelSelectorFrame.add(levelWrapperPanel, BorderLayout.CENTER);

        levelSelectorFrame.pack();
        levelSelectorFrame.setLocationRelativeTo(null);
        levelSelectorFrame.setVisible(true);
    }
}
