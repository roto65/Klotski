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

/**
 * Defines an object that can interact with Json files outside the program using the Gson library
 */
public class GsonFileParser {

    /**
     * Path of the file pointed by the instance
     */
    private final String path;

    /**
     * Constructor used to target file outside the program scope
     *
     * @param absolutePath the complete path of the file
     */
    public GsonFileParser(String absolutePath) {
        this.path = absolutePath;
    }

    /**
     * Constructor used to target file inside the program scope, like in the resources folder
     *
     * @param filename the name of the file
     * @param extension the extension, usually Json, of the file
     */
    public GsonFileParser(String filename, String extension) {
        this.path = filename + "." + extension;
    }

    /**
     * Method that saves the given schema to a Json file
     *
     * @param levelSchema the data that need to be saved
     */
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

    /**
     * Method that loads data from a Json files and parses it to a LevelSchema
     *
     * @param externalFile true if the file is placed outside the program scope
     * @return the newly generated level schema
     * @throws JsonSyntaxException if the file content doesn't match the Json syntax
     */
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