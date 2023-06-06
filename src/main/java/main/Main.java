package main;

import core.Board;
import core.Dashboard;
import ui.Window;

import javax.swing.*;

import static main.Constants.DEFAULT_BLOCK_CONFIGURATION;

public class Main {

    public static void main(String[] args) {

        // Initialize game object(s)
        Board board = new Board(DEFAULT_BLOCK_CONFIGURATION);

        Dashboard dashboard = new Dashboard(board);

        // Initialize ui
        SwingUtilities.invokeLater(() -> Window.initWindow(board.getBoardComponent(), dashboard.getDashboardComponent()));
    }
}
