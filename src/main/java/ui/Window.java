package ui;

import javax.swing.*;
import java.awt.*;

import static main.Constants.ROWS;
import static main.Constants.TITLE_SIZE;

public class Window {

    private static JFrame window;
    private static GridBagConstraints gridBagConstraints;

    public static void initWindow(BoardComponent boardComponent) {

        window = new JFrame("Klotski");

        window.addMouseListener(boardComponent);

        gridBagConstraints = new GridBagConstraints();

        window.setLayout(new GridBagLayout());

        gridBagConstraints.gridheight = ROWS;

        initLayout(boardComponent, new DashboardComponent());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(TITLE_SIZE * 7, TITLE_SIZE * 5);

        window.setResizable(false);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

    private static void initLayout(BoardComponent boardComponent, DashboardComponent dashboardComponent) {

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        window.add(boardComponent, gridBagConstraints);

        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridwidth = 2;
        window.add(dashboardComponent, gridBagConstraints);

    }

    public static void endGame(BoardComponent boardComponent) {

        window.removeMouseListener(boardComponent);
    }
}
