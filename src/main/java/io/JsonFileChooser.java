package io;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Defines a dialog that the user can use to visually save and load Json file on his computer
 */
public class JsonFileChooser {

    /**
     * Actual file chooser instance
     */
    private JFileChooser fileChooser;

    /**
     * Method that initializes the file chooser and sets its properties to match the operating system in use
     */
    public JsonFileChooser() {
        LookAndFeel backup = UIManager.getLookAndFeel();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            fileChooser = new JFileChooser();
            FileFilter jsonFilter = createJSONFileFilter();
            fileChooser.setFileFilter(jsonFilter);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } finally {
            try {
                UIManager.setLookAndFeel(backup);
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that defines a custom filter only for Json files
     *
     * @return the custom filter
     */
    private FileFilter createJSONFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "JSON files (*.json)";
            }
        };
    }

    /**
     * Initializes the save dialog and shows it to the user
     *
     * @return the file object selected by the user
     */
    public File showSaveDialog() {
        int saveValue = fileChooser.showSaveDialog(null);
        if (saveValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".json")) {
                // Append .json extension if missing
                selectedFile = new File(filePath + ".json");
            }

            return selectedFile;
        }
        return null;
    }

    /**
     * Initializes the load dialog and shows it to the user
     *
     * @return the file object selected by the user
     */
    public File showLoadDialog() {
        int openValue = fileChooser.showOpenDialog(null);
        if (openValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
