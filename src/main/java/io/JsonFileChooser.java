package io;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class JsonFileChooser {
    private JFileChooser fileChooser;

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

    public File showLoadDialog() {
        int openValue = fileChooser.showOpenDialog(null);
        if (openValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
