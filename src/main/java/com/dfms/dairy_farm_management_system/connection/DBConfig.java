package com.dfms.dairy_farm_management_system.connection;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.*;

public class DBConfig {
    private static MongoClient mongoClient;
    private static DB db;
    private static DBCollection collection;

    private static final String DBName = "test";

    public static void connect() throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        db = mongoClient.getDB(DBName);
    }

    public static void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static void setMongoClient(MongoClient mongoClient) {
        DBConfig.mongoClient = mongoClient;
    }

    public static DB getDb() {
        return db;
    }

    public static void setDb(DB db) {
        DBConfig.db = db;
    }

    public static DBCollection getCollection() {
        return collection;
    }

    public static void setCollection(DBCollection collection) {
        DBConfig.collection = collection;
    }
}
