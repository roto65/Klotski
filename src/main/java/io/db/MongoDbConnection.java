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
import org.bson.BsonInt32;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import ui.blocks.Block;

import java.util.ArrayList;
import java.util.List;

import static io.db.Credentials.mongoUri;
import static main.Constants.*;

// Ref: https://github.com/mongodb-university/atlas_starter_java/blob/master/src/main/java/mongodb/Main.java

/**
 * Provides all the functionality to exchange data from and to the database
 */
public class MongoDbConnection {

    /**
     * The database client object.
     * Needs a valid Uri to be initialized properly
     */
    private final MongoClient mongoClient;

    /**
     * The actual database object
     */
    private final MongoDatabase database;

    /**
     * Constructor that opens the db connection and initializes the db object
     *
     * @throws MongoException if connection cannot be established
     */
    @SuppressWarnings("ConstantValue")
    public MongoDbConnection() throws MongoException {

        if (mongoUri.equals("")) throw new MongoException("Invalid database Uri");

        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARN);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .build();

        try {
            mongoClient = MongoClients.create(settings);
        } catch (MongoException e) {
            e.printStackTrace();
            throw new MongoException("Cannot open connection");
        }

        // specific database objet
        database = mongoClient.getDatabase(DATABASE_NAME);

        testConnection();
    }

    /**
     * Method that tests the connection to the database sending a ping command
     *
     * @throws MongoException if connection cannot be established
     */
    private void testConnection() throws MongoException {
        Bson pingCommand = new BsonDocument("ping", new BsonInt32(1));
        try {
            database.runCommand(pingCommand);
        } catch (MongoException e) {
            throw new MongoException("Cannot ping MongoDb server");
        }
    }

    /**
     * Method that closes the connection once it has been used
     */
    public void closeClient() {
        mongoClient.close();
    }

    /**
     * @return the database object representing the hint collection
     */
    public MongoCollection<HintSchema> getHintCollection() {

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new PointCodec()),
                CodecRegistries.fromProviders(new MoveCodecProvider(), new HintSchemaCodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry()
                );

        return database.getCollection(HINT_COLLECTION, HintSchema.class).withCodecRegistry(codecRegistry);
    }

    /**
     * Method that loads the given hints in the database
     *
     * @param hints list containing the hints to be loaded
     */
    public void uploadHints(List<HintSchema> hints) {
        MongoCollection<HintSchema> hintCollection = getHintCollection();

        hints.removeIf(s -> findHint(s.getState()) != null);

        if (hints.isEmpty()) return;

        hintCollection.insertMany(hints);
    }

    /**
     * Method that queries the database to find a hint for the given board configuration
     *
     * @param state string that represents the current board configuration
     * @return hint if existing
     */
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

    /**
     * @return the database object representing the level collection
     */
    public MongoCollection<BsonDocument> getLevelCollection() {
        return database.getCollection(LEVELS_COLLECTION, BsonDocument.class);
    }

    /**
     * Method that queries the database to find a level that matches the given level number
     *
     * @param levelNumber the number of the level to be found
     * @return corresponding level schema if existing
     */
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
