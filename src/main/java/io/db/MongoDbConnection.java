package io.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import core.Move;
import io.db.codecs.HintSchemaCodecProvider;
import io.db.codecs.MoveCodecProvider;
import io.db.codecs.PointCodec;
import io.schemas.HintSchema;
import io.schemas.LevelSchema;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import ui.blocks.Block;

import java.util.ArrayList;
import java.util.List;

import static io.db.Credentials.mongoUri;
import static main.Constants.*;

// Ref: https://github.com/mongodb-university/atlas_starter_java/blob/master/src/main/java/mongodb/Main.java

public class MongoDbConnection {

    private final MongoDatabase database;

    public MongoDbConnection() {

        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARN);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .build();

        MongoClient mongoClient = null;

        try {
            mongoClient = MongoClients.create(settings);
        } catch (MongoException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // specific database objet
        database = mongoClient.getDatabase(databaseName);
    }

    public MongoCollection<HintSchema> getHintCollection() {

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new PointCodec()),
                CodecRegistries.fromProviders(new MoveCodecProvider(), new HintSchemaCodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry()
                );

        return database.getCollection(hintCollection, HintSchema.class).withCodecRegistry(codecRegistry);
    }

    public void uploadHints(List<HintSchema> hints) {
        MongoCollection<HintSchema> hintCollection = getHintCollection();

        hints.removeIf(s -> findHint(s.getState()) != null);

        if (hints.isEmpty()) return;

        hintCollection.insertMany(hints);
    }

    public Move findHint(String state) {

        Bson stateFilter = Filters.eq("state", state);

        try {
            HintSchema result = getHintCollection().find(stateFilter).limit(1).first();
            if (result != null) {
                return result.getBestMove();
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MongoCollection<BsonDocument> getLevelCollection() {
        return database.getCollection(levelsCollection, BsonDocument.class);
    }

    public LevelSchema getLevel(int levelNumber) {

        Bson levelFilter = Filters.eq("level", levelNumber);

        try {
            BsonDocument doc = getLevelCollection().find(levelFilter).limit(1).first();

            if (doc == null) {
                return null;
            }

            int minimumMoves = doc.get("mini").asInt32().getValue();

            doc.remove("_id");
            doc.remove("level");
            doc.remove("mini");

            ArrayList<Block> blocks = new BsonParser().load(doc.toJson());

            return new LevelSchema(levelNumber, blocks, minimumMoves);
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
