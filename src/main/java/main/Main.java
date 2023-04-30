package main;

import core.Board;
import ui.Window;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Initialize game object(s)
        Board board = new Board("largeTest");

        // Initialize ui
        SwingUtilities.invokeLater(() -> Window.initWindow(board.getBoardComponent()));
    }
}

// inspo: http://simonsays-tw.com/web/Klotski/game/klotskiDemo.html