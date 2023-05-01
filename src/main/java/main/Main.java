package main;

import core.Board;
import ui.Window;

import javax.swing.*;

import static main.Constants.DEFAULT_BLOCK_CONFIGURATION;

public class Main {

    public static void main(String[] args) {

        // Initialize game object(s)
        Board board = new Board(DEFAULT_BLOCK_CONFIGURATION);

        // Initialize ui
        SwingUtilities.invokeLater(() -> Window.initWindow(board.getBoardComponent()));
    }
}

// inspo: http://simonsays-tw.com/web/Klotski/game/klotskiDemo.html