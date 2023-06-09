package io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class IOUtils {

    public static Reader readFromJson(String filename) throws IOException{
        String path = "layout/" + filename;

        InputStream inputStream = IOUtils.class.getClassLoader().getResourceAsStream(path);
        assert inputStream != null;
        return new InputStreamReader(inputStream);
    }

    public static Writer writeToJson(String path) throws IOException {
        return new FileWriter(path);
    }

    public static Image readFromPng(String filename) throws IOException{
        String path = "drawable/" + filename;

        InputStream inputStream = IOUtils.class.getClassLoader().getResourceAsStream(path);
        assert inputStream != null;
        return ImageIO.read(inputStream);
    }

    public static Font readFromTtf(String filename) throws IOException, FontFormatException {
        String path = "font/" + filename;

        InputStream inputStream = IOUtils.class.getClassLoader().getResourceAsStream(path);
        assert inputStream != null;
        return Font.createFont(Font.TRUETYPE_FONT, inputStream);
    }
}