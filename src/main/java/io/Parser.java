package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ui.blocks.Block;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    private final String filename;
    private final String extension;

    public Parser(String filename) {
        this.filename = filename;
        extension = "json";
    }

    public Parser(String filename, String extension) {
        this.filename = filename;
        this.extension = extension;
    }

    public void save(ArrayList<Block> blocks) {

        String path = "src/main/resources/layout/" + filename + "." + extension;

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter()).setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        try {
            FileWriter fileWriter = new FileWriter(path);

            gson.toJson(blocks, fileWriter);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Block> load() {

        String path = "src/main/resources/layout/" + filename + "." + extension;

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter());

        Gson gson = gsonBuilder.create();

        ArrayList<Block> blocks = null;

        try {
            FileReader fileReader = new FileReader(path);

            blocks = gson.fromJson(fileReader, new TypeToken<ArrayList<Block>>(){}.getType());
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return blocks;
    }
}
