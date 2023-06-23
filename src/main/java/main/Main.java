package main;

import core.Board;
import core.Dashboard;
import ui.Window;

import javax.swing.*;

import static main.Constants.LAYOUT_DEFAULT;

public class Main {

    public static void main(String[] args) {

        // Initialize game object(s)
        Board board = new Board(LAYOUT_DEFAULT);

        Dashboard dashboard = new Dashboard(board);

        // Initialize ui
        SwingUtilities.invokeLater(() -> Window.initWindow(board.getBoardComponent(), dashboard.getDashboardComponent()));
    }
}
