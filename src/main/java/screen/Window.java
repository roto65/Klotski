package screen;

import javax.swing.*;
import java.awt.*;

public class Window {

    public static void initWindow() {

        JFrame window = new JFrame("Klotski");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setMinimumSize(new Dimension(400, 600));

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
