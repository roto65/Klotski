package ui;

import io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static main.Constants.ROWS;

/**
 * Defines the main window for the program's gui
 */
public class Window {

    /**
     * The actual window instance
     */
    private static JFrame window;

    /**
     * Constraints for the window layout
     */
    private static GridBagConstraints gridBagConstraints;

    /**
     * The icon used for all the windows in the program
     * Usually showed in the top-left corner in Windows
     */
    private static Image windowIcon;

    /**
     * Method that initializes the window and all the widgets contained inside it
     *
     * @param boardComponent ui component for the board object
     * @param dashboardComponent ui component for the dashboard object
     */
    public static void initWindow(BoardComponent boardComponent, DashboardComponent dashboardComponent) {

        window = new JFrame("Klotski");

        newGame(boardComponent);

        gridBagConstraints = new GridBagConstraints();

        window.setLayout(new GridBagLayout());

        gridBagConstraints.gridheight = ROWS;

        initLayout(boardComponent, dashboardComponent);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            windowIcon = IOUtils.readFromPng("Icon.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        window.setIconImage(windowIcon);

        window.setResizable(false);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

    /**
     * Method that sets the constraints parameters adds the widgets to the canvas
     *
     * @param boardComponent the ui component for the board object
     * @param dashboardComponent the ui component for the dashboard object
     */
    private static void initLayout(BoardComponent boardComponent, DashboardComponent dashboardComponent) {

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        window.add(boardComponent, gridBagConstraints);

        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridwidth = 2;
        window.add(dashboardComponent, gridBagConstraints);

    }

    /**
     * Method that removes the mouse listener from the board component
     *
     * @param boardComponent ui component for the board object
     */
    public static void endGame(BoardComponent boardComponent) {
        window.removeMouseListener(boardComponent);
    }

    /**
     * Method that adds the mouse listener to the board component
     *
     * @param boardComponent ui component for the board object
     */
    public static void newGame(BoardComponent boardComponent) {
        window.addMouseListener(boardComponent);
    }

    /**
     * @return the icon of the program, used in the top-left corner of all dialogs
     */
    public static Image getWindowIcon() {
        return windowIcon;
    }
}
