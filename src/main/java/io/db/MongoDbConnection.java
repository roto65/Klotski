package io.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import core.HintSchema;
import core.Move;
import io.db.codecs.HintSchemaCodecProvider;
import io.db.codecs.MoveCodecProvider;
import io.db.codecs.PointCodec;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.util.List;

import static io.db.Credentials.mongoUri;
import static main.Constants.*;

// Ref: https://github.com/mongodb-university/atlas_starter_java/blob/master/src/main/java/mongodb/Main.java

public class MongoDbConnection {

    private final MongoDatabase database;

    private final MongoCollection<BsonDocument> collection;

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
        // specific collection object
        collection = database.getCollection(collectionName, BsonDocument.class);
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

        //getHintCollection().deleteMany(new Document());

        Bson stateFilter = Filters.eq("state", state);

        try {
            HintSchema result = getHintCollection().find(stateFilter).limit(1).first();
            //System.out.println(result);
            if (result != null) {
                return result.getBestMove();
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFirst() {
        for (BsonDocument nextDoc : collection.find()) {
            nextDoc.remove("_id");

            return nextDoc.toJson();
        }

        return null;
    }

    public void insert(String jsonString) {
        Document document = Document.parse(jsonString);

        collection.insertOne(document.toBsonDocument());
    }
}
