package core;

import ui.StyledButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static main.Constants.TITLE_SIZE;

/**
 * Defines a new button with custom styling
 */
public class StyledButton extends JButton {

    /**
     * This variable indicates if the button is pressed or not
     */
    private boolean pressed = false;

    /**
     * This variable represents the position of the button inside the gird.
     * If pos is null no information is available
     */
    private final Point pos;

    /**
     * Constructor method for the button, creates the associated UI
     *
     * @param text what is written on the button
     * @param pos the position of the button in the grid that contains it
     */
    public StyledButton(String text, Point pos) {
        super(text);

        setPreferredSize(new Dimension(TITLE_SIZE, (TITLE_SIZE * 4) / 5));

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

    /**
     * Constructor method for the button if the position needs to be null
     * @param text what is written on the button
     */
    public StyledButton(String text) {
        this(text, null);
    }

    /**
     * @return true if the button is pressed
     */
    public boolean isPressed() {
        return pressed ;
    }

    /**
     * @return the position of the button inside the grid
     */
    public Point getPos() {
        return pos;
    }
}

