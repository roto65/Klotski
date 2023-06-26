package ui.dialogs;

import core.StyledButton;
import ui.PageCounter;
import ui.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static main.Constants.*;

/**
 * Defines a dialog window used when the user wants to select a new level to play
 */
public class LevelSelectorDialog extends JFrame {

    /**
     * The actual dialog instance
     */
    private JDialog levelSelectorDialog;

    /**
     * The layout portion that contains all the buttons for the levels
     */
    private JPanel levelWrapperPanel;

    /**
     * The indicator of the page currently shown
     */
    private final PageCounter pageCounter;

    /**
     * The number of the page currently shown
     */
    private int currentPage = 1;

    /**
     * The number of the level the user selected
     */
    private int selectedLevel = -1;

    /**
     * The number of buttons per page, calculated with values from Constants
     *
     * @see main.Constants
     */
    private final int buttonsPerPage;

    /**
     * The number of pages, calculated with values from Constants
     *
     * @see main.Constants
     */
    private final int totalPages;

    /**
     * Constructor method that initializes the dialog's data
     */
    public LevelSelectorDialog() {
        buttonsPerPage = LEVEL_SELECTOR_GRID_ROWS * LEVEL_SELECTOR_GRID_COLUMNS;
        totalPages = (int) Math.ceil((double) LEVELS / buttonsPerPage);

        pageCounter = new PageCounter(totalPages);
    }

    /**
     * @return the number of the level selected by the user, -1 if the choice wasn't made
     */
    public int getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * Method that defines dialog properties and widgets, including all the layouts. Then it shows it to the user
     */
    public void showDialog() {
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
        levelSelectorDialog.setResizable(false);
        levelSelectorDialog.setLocationRelativeTo(null);
        levelSelectorDialog.setVisible(true);
    }

    /**
     * Method used by the <i>prev</i> button to switch page
     */
    private void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            ((CardLayout) levelWrapperPanel.getLayout()).show(levelWrapperPanel, String.valueOf(currentPage));

            pageCounter.setCurrentPage(currentPage);
        }
    }

    /**
     * Method used by the <i>next</i> button to switch page
     */
    private void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            ((CardLayout) levelWrapperPanel.getLayout()).show(levelWrapperPanel, String.valueOf(currentPage));

            pageCounter.setCurrentPage(currentPage);
        }
    }

    /**
     * Method in charge of creating all the buttons for all the different levels and organizing them in different pages
     */
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
