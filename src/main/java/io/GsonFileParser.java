package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.schemas.LevelSchema;
import ui.blocks.Block;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class GsonFileParser {

    private final String path;

    public GsonFileParser(String absolutePath) {
        this.path = absolutePath;
    }

    public GsonFileParser(String filename, String extension) {
        this.path = filename + "." + extension;
    }

    public void save(LevelSchema levelSchema) {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter()).setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        try {
            Writer writer = IOUtils.writeToJson(path);

            gson.toJson(levelSchema, writer);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LevelSchema load(boolean externalFile) throws JsonSyntaxException {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter());

        Gson gson = gsonBuilder.create();

        LevelSchema levelSchema = null;
        Reader reader;

        try {
            if (externalFile) {
                reader = new FileReader(path);
            } else {
                reader = IOUtils.readFromJson(path);
            }
            levelSchema = gson.fromJson(reader, new TypeToken<LevelSchema>(){}.getType());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert levelSchema != null : "Error loading layout file";
        return levelSchema;
    }
}