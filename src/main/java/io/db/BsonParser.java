package io.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.BlockAdapter;
import io.db.schemas.LevelSchema;
import ui.blocks.Block;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class BsonParser {

    public BsonParser() {
    }

    // OLD
    @SuppressWarnings("unused")
    public void save(ArrayList<Block> blocks) {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter()).setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        Writer writer = new StringWriter();

        gson.toJson(blocks, writer);
    }

    public ArrayList<Block> load(String JsonString) {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter());

        Gson gson = gsonBuilder.create();

        Reader reader = new StringReader(JsonString); // deve caricare la stringa con formato Json

        LevelSchema collection = gson.fromJson(reader, new TypeToken<LevelSchema>(){}.getType());

        return collection.getBlocks();
    }
}
