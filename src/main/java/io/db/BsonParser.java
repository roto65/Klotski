package io.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.BlockCollection;
import io.BlockAdapter;
import ui.blocks.Block;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class BsonParser {

    private final MongoDbConnection connection;

    public BsonParser() {
        connection = new MongoDbConnection();
    }

    public void save(ArrayList<Block> blocks) {
        BlockCollection collection = new BlockCollection(blocks);

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter()).setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        Writer writer = new StringWriter();

        gson.toJson(collection, writer);

        connection.insert(writer.toString());
    }

    public ArrayList<Block> load() {

        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Block.class, new BlockAdapter());

        Gson gson = gsonBuilder.create();

        Reader reader = new StringReader(connection.getFirst()); // deve caricare la stringa con formato Json

        BlockCollection collection = gson.fromJson(reader, new TypeToken<BlockCollection>(){}.getType());

        return collection.getBlocks();
    }
}
