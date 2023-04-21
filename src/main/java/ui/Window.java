package ui;

import javax.swing.*;


public class Window {

    public static void initWindow() {

        JFrame window = new JFrame("Klotski");

        window.add(new Board());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(false);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
