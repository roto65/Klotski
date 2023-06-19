package ui;

import io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.Constants.ROWS;

public class Window {

    private static JFrame window;
    private static GridBagConstraints gridBagConstraints;

    public static void initWindow(BoardComponent boardComponent, DashboardComponent dashboardComponent) {

        window = new JFrame("Klotski");

        newGame(boardComponent);

        gridBagConstraints = new GridBagConstraints();

        window.setLayout(new GridBagLayout());

        gridBagConstraints.gridheight = ROWS;

        initLayout(boardComponent, dashboardComponent);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            window.setIconImage(IOUtils.readFromPng("Icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public static void newGame(BoardComponent boardComponent) {
        window.addMouseListener(boardComponent);
    }
}
