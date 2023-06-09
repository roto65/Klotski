package ui;

import core.StyledButton;

import javax.swing.*;
import java.awt.*;

public class LevelSelector extends JFrame {
    private static final int ROWS = 4;
    private static final int COLUMNS = 7;
    private static final int BUTTONS_PER_PAGE = ROWS * COLUMNS;
    private int currentPage = 1;
    private int totalPages;

    public LevelSelector() {
        setTitle("Main Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton levelSelectorButton = new JButton("Select Level");
        levelSelectorButton.addActionListener(e -> showLevelSelector(currentPage));
        add(levelSelectorButton);

        // Add other components to your main game window

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showLevelSelector(int page) {
        JFrame levelSelectorFrame = new JFrame("Level Selector");
        levelSelectorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        levelSelectorFrame.setLayout(new BorderLayout());

        JPanel levelWrapperPanel = new JPanel(new CardLayout());

        JPanel pageButtonPanel = new JPanel(new FlowLayout());
        pageButtonPanel.setBackground(Color.GREEN);
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
        totalPages = (int) Math.ceil((double) 420 / BUTTONS_PER_PAGE);

        for (int j = 1; j <= totalPages; j++) {
            JPanel levelPanel = new JPanel(new GridLayout(ROWS, COLUMNS, 10, 10));

            levelPanel.setBackground(Color.MAGENTA);

            for (int i = 1; i <= BUTTONS_PER_PAGE; i++) {
                JButton levelButton = new StyledButton("Level " + (i + offset), new Point(0 ,0));
                final int levelNumber = (i + offset);
                levelButton.addActionListener(e -> System.out.println("Level " + levelNumber + " selected"));
                levelPanel.add(levelButton);
            }
            offset += BUTTONS_PER_PAGE;
            levelWrapperPanel.add(levelPanel, String.valueOf(j));
        }

        levelSelectorFrame.add(pageButtonPanel, BorderLayout.SOUTH);
        levelSelectorFrame.add(levelWrapperPanel, BorderLayout.CENTER);

        levelSelectorFrame.pack();
        levelSelectorFrame.setLocationRelativeTo(null);
        levelSelectorFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LevelSelector::new);
    }
}
