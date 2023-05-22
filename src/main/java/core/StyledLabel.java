package core;

import ui.StyledButtonUI;
import ui.StyledLabelUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import static main.Constants.TITLE_SIZE;

public class StyledLabel extends JLabel {

    private Point pos;
    private String staticText;
    private String variableText;

    public StyledLabel(String staticText, String variableText, Point pos) {
        super();

        setPreferredSize(new Dimension(TITLE_SIZE, TITLE_SIZE / 2));

        setUI(new StyledLabelUI());

        this.staticText = staticText;
        this.variableText = variableText;
        this.pos = pos;

        setText(staticText + variableText);
    }

    public Point getPos() {
        return pos;
    }

    public String getStaticText() {
        return staticText;
    }

    public String getVariableText() {
        return variableText;
    }

    public void setVariableText(String variableText) {
        this.variableText = variableText;
    }
}
