package io.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.BlockAdapter;
import io.schemas.LevelSchema;
import ui.blocks.Block;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Provides a custom parser between the block class and the Bson objects using the Gson library
 */
public class BsonParser {

    /**
     * Default constructor for the BsonParser objects
     */
    public BsonParser() {
    }

    /**
     * Method that writes in Json format the input data
     *
     * @param blocks the list of blocks that needs to be parsed
     * @deprecated
     */
    @Deprecated
    @SuppressWarnings("unused")
    public void save(ArrayList<Block> blocks) {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter()).setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        Writer writer = new StringWriter();

        gson.toJson(blocks, writer);
    }

    /**
     * Method that reads block data in Json format and returns it as a list of objects
     *
     * @param JsonString the Json data tht needs to be parsed
     * @return a list containing block objects
     */
    public ArrayList<Block> load(String JsonString) {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter());

        Gson gson = gsonBuilder.create();

        Reader reader = new StringReader(JsonString); // deve caricare la stringa con formato Json

        LevelSchema collection = gson.fromJson(reader, new TypeToken<LevelSchema>(){}.getType());

        return collection.getBlocks();
    }
}
