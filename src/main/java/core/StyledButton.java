package core;

import ui.StyledButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static main.Constants.TITLE_SIZE;

public class StyledButton extends JButton {

    private boolean pressed = false;

    private final Point pos;

    public StyledButton(String text, Point pos) {
        super(text);

        setPreferredSize(new Dimension(TITLE_SIZE, TITLE_SIZE));

        setContentAreaFilled(false);
        setBorderPainted(false);

        setUI(new StyledButtonUI());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                 pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                 pressed = false;
            }
        });

        this.pos = pos;
    }

    public boolean isPressed() {
        return pressed ;
    }

    public Point getPos() {
        return pos;
    }
}

