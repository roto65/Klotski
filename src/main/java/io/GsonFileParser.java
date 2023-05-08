package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.BlockCollection;
import ui.blocks.Block;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GsonFileParser {

    private final String filename;
    private final String extension;

    public GsonFileParser(String filename) {
        this.filename = filename;
        extension = "json";
    }

    public GsonFileParser(String filename, String extension) {
        this.filename = filename;
        this.extension = extension;
    }

    public void save(ArrayList<Block> blocks) {

        BlockCollection collection = new BlockCollection(blocks);

        String path = "src/main/resources/layout/" + filename + "." + extension;

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter()).setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        try {
            FileWriter fileWriter = new FileWriter(path);

            gson.toJson(collection, fileWriter);

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

        BlockCollection collection = null;

        try {
            FileReader fileReader = new FileReader(path);

            collection = gson.fromJson(fileReader, new TypeToken<BlockCollection>(){}.getType());
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert collection != null : "Error loading layout file";
        return collection.getBlocks();
    }
}