package ui;

import core.StyledButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static main.Constants.*;

public class LevelSelector extends JFrame {

    private JDialog levelSelectorDialog;
    private JPanel levelWrapperPanel;

    private final PageCounter pageCounter;

    private int currentPage = 1;
    private int selectedLevel = -1;

    private final int buttonsPerPage;
    private final int totalPages;

    public LevelSelector() {
        buttonsPerPage = LEVEL_SELECTOR_GRID_ROWS * LEVEL_SELECTOR_GRID_COLUMNS;
        totalPages = (int) Math.ceil((double) LEVELS / buttonsPerPage);

        pageCounter = new PageCounter(totalPages);
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void showLevelSelector() {
        levelSelectorDialog = new JDialog(this, "Level Selector", true);
        levelSelectorDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        levelSelectorDialog.setLayout(new BorderLayout());

        levelWrapperPanel = new JPanel(new CardLayout());
        levelWrapperPanel.setBackground(COLOR_BACKGROUND);

        int borderSize = TITLE_SIZE / 10;
        levelWrapperPanel.setBorder(new EmptyBorder(new Insets(borderSize, borderSize, 0, borderSize)));

        populateLevelButtons();

        JPanel pageButtonPanel = new JPanel(new FlowLayout());
        pageButtonPanel.setBackground(COLOR_BACKGROUND);

        JButton prevButton = new StyledButton("Prev");
        prevButton.addActionListener(e -> prevPage());
        pageButtonPanel.add(prevButton);

        pageButtonPanel.add(pageCounter);

        JButton nextButton = new StyledButton("Next");
        nextButton.addActionListener(e -> nextPage());
        pageButtonPanel.add(nextButton);

        levelSelectorDialog.setIconImage(Window.getWindowIcon());

        levelSelectorDialog.add(levelWrapperPanel, BorderLayout.CENTER);
        levelSelectorDialog.add(pageButtonPanel, BorderLayout.SOUTH);

        levelSelectorDialog.pack();
        levelSelectorDialog.setLocationRelativeTo(null);
        levelSelectorDialog.setVisible(true);
    }

    private void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            ((CardLayout) levelWrapperPanel.getLayout()).show(levelWrapperPanel, String.valueOf(currentPage));

            pageCounter.setCurrentPage(currentPage);
        }
    }

    private void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            ((CardLayout) levelWrapperPanel.getLayout()).show(levelWrapperPanel, String.valueOf(currentPage));

            pageCounter.setCurrentPage(currentPage);
        }
    }

    private void populateLevelButtons() {
        int offset = 0;

        for (int j = 1; j <= totalPages; j++) {
            JPanel levelPanel = new JPanel(new GridLayout(LEVEL_SELECTOR_GRID_ROWS, LEVEL_SELECTOR_GRID_COLUMNS, 10, 10));

            levelPanel.setBackground(COLOR_BACKGROUND);

            for (int i = 1; i <= buttonsPerPage; i++) {
                JButton levelButton = new StyledButton("Level " + (i + offset), new Point(0 ,0));
                final int levelNumber = (i + offset);
                levelButton.addActionListener(e -> {
                    selectedLevel = levelNumber;
                    levelSelectorDialog.dispose();
                });
                levelPanel.add(levelButton);
            }
            offset += buttonsPerPage;
            levelWrapperPanel.add(levelPanel, String.valueOf(j));
        }
    }
}
