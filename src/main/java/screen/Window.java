package screen;

import javax.swing.*;
import java.awt.*;

import static main.Constants.*;

public class Window {

    public static void initWindow() {

        JFrame window = new JFrame("Klotski");

        window.add(new Board());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //window.setMinimumSize(new Dimension(TITLE_SIZE * COLUMNS, TITLE_SIZE * ROWS));

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
