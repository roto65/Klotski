package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.BlockCollection;
import ui.blocks.Block;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class GsonFileParser {

    private final String path;

    public GsonFileParser(String absolutePath) {
        this.path = absolutePath;
    }

    public GsonFileParser(String filename, String extension) {
        this.path = filename + "." + extension;
    }
    public void save(ArrayList<Block> blocks) {

        BlockCollection collection = new BlockCollection(blocks);

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter()).setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        try {
            Writer writer = IOUtils.writeToJson(path);

            gson.toJson(collection, writer);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Block> load(boolean externalFile) {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter());

        Gson gson = gsonBuilder.create();

        BlockCollection collection = null;
        Reader reader = null;

        try {
            if (externalFile) {
                reader = new FileReader(path);
            } else {
                reader = IOUtils.readFromJson(path);
            }
            collection = gson.fromJson(reader, new TypeToken<BlockCollection>(){}.getType());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert collection != null : "Error loading layout file";
        return collection.getBlocks();
    }
}