package ui.dialogs;

import core.StyledButton;
import io.IOUtils;
import ui.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static main.Constants.COLOR_BACKGROUND;
import static main.Constants.TITLE_SIZE;

/**
 * Defines a dialog window used when a database error occurs
 */
public class DbErrorDialog extends JFrame {

    /**
     * The message to be displayed
     */
    private final String errorMsg;

    /**
     * Constructor method that initializes the dialog's data
     * @param msg the message to be displayed
     */
    public DbErrorDialog(String msg) {
        this.errorMsg = msg;
    }

    /**
     * Method that defines dialog properties and widgets, including all the layouts. Then it shows it to the user
     */
    public void showDialog() {
        JDialog dialog = new JDialog(this, "Database Error", true);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(COLOR_BACKGROUND);
        wrapperPanel.setPreferredSize(new Dimension(TITLE_SIZE * 4, TITLE_SIZE * 2));

        int borderSize = TITLE_SIZE / 10;
        wrapperPanel.setBorder(new EmptyBorder(new Insets(borderSize, borderSize, 0, borderSize)));

        JLabel label = new JLabel(errorMsg);
        label.setBackground(COLOR_BACKGROUND);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        try {
            label.setFont(IOUtils.readFromTtf("DotGothic.ttf").deriveFont(Font.BOLD, (float) TITLE_SIZE / 4));
        } catch (IOException | FontFormatException e) {
            System.out.println("Error opening font file: " + e.getMessage());
            return;
        }

        JPanel buttonWrapperPanel = new JPanel(new FlowLayout());
        buttonWrapperPanel.setBackground(COLOR_BACKGROUND);

        JButton okButton = new StyledButton("Ok");
        okButton.addActionListener(e -> dialog.dispose());

        JButton exitButton = new StyledButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        buttonWrapperPanel.add(okButton);
        buttonWrapperPanel.add(exitButton);

        wrapperPanel.add(label, BorderLayout.CENTER);
        wrapperPanel.add(buttonWrapperPanel, BorderLayout.SOUTH);

        dialog.add(wrapperPanel);

        dialog.setIconImage(Window.getWindowIcon());

        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
