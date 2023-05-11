package ui;

import javax.swing.*;
import java.awt.*;

import static main.Constants.ROWS;
import static main.Constants.TITLE_SIZE;

public class DashboardComponent extends JPanel {

    public DashboardComponent() {

        setPreferredSize(new Dimension(TITLE_SIZE * 2, TITLE_SIZE * ROWS));

        setBackground(new Color(255,91,46));

        Button button = new Button("Yee");

        add(button);

    }

    /*
    Buttons:
        - New Game (Reset)
        - Change loadout
        - Save
        - Load
        - Undo
        - Best Move
        - Exit
    Move counter
     */


}
