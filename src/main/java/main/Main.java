package main;

import ui.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(Window::initWindow);
    }
}