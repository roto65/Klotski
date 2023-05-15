package main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class CustomButtonExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        // Create a JFrame to hold the button
        JFrame frame = new JFrame("Custom Button Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(Color.RED);

        // Create a custom Icon
        ImageIcon customIcon = new ImageIcon("src/main/resources/drawable/Button.png");

        // Create the JButton
        JButton customButton = new JButton("Custom Button");

        customButton.setContentAreaFilled(false);
        customButton.setBorderPainted(false);

        customButton.addActionListener(e -> System.out.print("Culissimo"));

        // Set custom UI for the button
        customButton.setUI(new CustomButtonUI(customIcon));

        // Add the button to the frame
        frame.getContentPane().add(customButton, BorderLayout.EAST);

        // Adjust the frame size and make it visible
        frame.pack();
        frame.setVisible(true);
    }

    // Custom ButtonUI class
    static class CustomButtonUI extends BasicButtonUI {
        private final ImageIcon backgroundIcon;

        public CustomButtonUI(ImageIcon backgroundIcon) {
            this.backgroundIcon = backgroundIcon;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;

            // Get the dimensions of the button
            int width = button.getWidth();
            int height = button.getHeight();

            // Scale the background image to match the button size
            if (backgroundIcon != null) {
                Image scaledImage = backgroundIcon.getImage().getScaledInstance(width, height, Image.SCALE_FAST);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                g.drawImage(scaledIcon.getImage(), 0, 0, null);
            }

            // Let the default button UI paint the text and other decorations
            super.paint(g, c);
        }
    }
}