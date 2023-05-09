package io.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.bson.BsonDocument;
import org.bson.Document;

import static io.db.Credentials.mongoUri;
import static main.Constants.collectionName;
import static main.Constants.databaseName;

// Ref: https://github.com/mongodb-university/atlas_starter_java/blob/master/src/main/java/mongodb/Main.java

public class MongoDbConnection {

    private final MongoDatabase database;

    private MongoCollection<BsonDocument> collection;

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

    public void setCollection(String collectionName) {
        collection = database.getCollection(collectionName, BsonDocument.class);
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
