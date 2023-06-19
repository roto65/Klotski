package ui;

import core.StyledButton;
import core.StyledLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static main.Constants.ROWS;
import static main.Constants.TITLE_SIZE;

public class DashboardComponent extends JPanel {

    private final Color BgLight, BgDark;

    public DashboardComponent(ArrayList<StyledButton> buttons, ArrayList<StyledLabel> labels) {

        setPreferredSize(new Dimension(TITLE_SIZE * 3, TITLE_SIZE * ROWS));

        setBackground(new Color(255,91,46));

        BgDark  = new Color(255,91,46);
        BgLight = new Color(255,91,46);

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // padding settings to put some space between the buttons
        constraints.insets = new Insets(TITLE_SIZE / 16, TITLE_SIZE / 8, TITLE_SIZE / 16, TITLE_SIZE / 8);

        for (StyledButton button : buttons) {

            constraints.gridx = button.getPos().x;
            constraints.gridy = button.getPos().y;

            add(button, constraints);
        }

        constraints.insets = new Insets(TITLE_SIZE / 16, 0, 0, 0);

        for (StyledLabel label : labels) {

            constraints.gridx = label.getPos().x;
            constraints.gridy = label.getPos().y;

            add(label, constraints);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform backup = g2d.getTransform();

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < 3; column++) {
                if ((row + column) % 2 == 0) {
                    g2d.setColor(BgLight);
                } else {
                    g2d.setColor(BgDark);
                }
                g2d.fillRect(column * TITLE_SIZE, row * TITLE_SIZE, TITLE_SIZE, TITLE_SIZE);
            }
        }

        g2d.setTransform(backup);
    }

}
