package ui;

import javax.swing.*;

public class Window {

    private static JFrame window;

    public static void initWindow(BoardComponent boardComponent) {

        window = new JFrame("Klotski");

        window.addMouseListener(boardComponent);

        window.add(boardComponent);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(false);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

    public static void endGame(BoardComponent boardComponent) {

        window.removeMouseListener(boardComponent);
    }
}
