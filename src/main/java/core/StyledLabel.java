package core;

import ui.StyledLabelUI;

import javax.swing.*;
import java.awt.*;

import static main.Constants.TITLE_SIZE;

/**
 * Defines a new label with custom styling
 */
public class StyledLabel extends JLabel {

    /**
     * This variable represents the position of the label inside the grid.
     * If pos is null no information is available
     */
    private final Point pos;

    /**
     * This string represents the part of the label text that can not change
     */
    private final String staticText;

    /**
     * This string represents the part of the label text that can change
     */
    private String variableText;

    /**
     * Constructor method for the label, creates the associated UI
     *
     * @param staticText what is written on the label, this cannot change
     * @param variableText what is written on the label, this can change afterward
     * @param pos the position of the label in the grid that contains it
     */
    public StyledLabel(String staticText, String variableText, Point pos) {
        super();

        setPreferredSize(new Dimension(TITLE_SIZE, TITLE_SIZE / 2));

        setUI(new StyledLabelUI());

        this.staticText = staticText;
        this.variableText = variableText;
        this.pos = pos;

        setText(staticText + variableText);
    }

    /**
     * @return the position of the label in the grid that contains it
     */
    public Point getPos() {
        return pos;
    }

    /**
     * @return the part of the text that cannot change
     */
    @SuppressWarnings("unused")
    public String getStaticText() {
        return staticText;
    }

    /**
     * @return the part of the text that can change
     */
    public String getVariableText() {
        return variableText;
    }

    /**
     * @param variableText the text that replaces the previous variable text
     */
    public void setVariableText(String variableText) {
        this.variableText = variableText;

        setText(staticText + variableText);
    }
}
