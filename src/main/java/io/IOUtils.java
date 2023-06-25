package io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

import static main.Constants.*;

/**
 * Defines utility methods to save and load various types of file
 */
public class IOUtils {

    /**
     * Loads data from a Json file
     *
     * @param filename the name of the file to be red
     * @return a Reader bound to that file
     * @throws IOException if the file cannot be found
     */
    public static Reader readFromJson(String filename) throws IOException{
        String path = FOLDER_LAYOUTS + filename;

        InputStream inputStream = IOUtils.class.getClassLoader().getResourceAsStream(path);
        assert inputStream != null;
        return new InputStreamReader(inputStream);
    }

    /**
     * Saves a file to a Json file
     *
     * @param path the path of the file to be written
     * @return a Writer bound to that file
     * @throws IOException if the file cannot be found
     */
    public static Writer writeToJson(String path) throws IOException {
        return new FileWriter(path);
    }

    /**
     * Loads data from a png file
     *
     * @param filename the name of the file to be red
     * @return an Image bound to that file
     * @throws IOException if the file cannot be found
     */
    public static Image readFromPng(String filename) throws IOException{
        String path = FOLDER_IMAGES + filename;

        InputStream inputStream = IOUtils.class.getClassLoader().getResourceAsStream(path);
        assert inputStream != null;
        return ImageIO.read(inputStream);
    }

    /**
     * Loads data from a ttf font file
     *
     * @param filename the name of the file to be red
     * @return a Font bound to that file
     * @throws IOException if the file cannot be found
     * @throws FontFormatException if the font cannot be loaded properly
     */
    public static Font readFromTtf(String filename) throws IOException, FontFormatException {
        String path = FOLDER_FONTS + filename;

        InputStream inputStream = IOUtils.class.getClassLoader().getResourceAsStream(path);
        assert inputStream != null;
        return Font.createFont(Font.TRUETYPE_FONT, inputStream);
    }

    /**
     * Deleted constructor
     * This class cannot be instanced
     */
    private IOUtils() {
    }
}
