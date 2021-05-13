package com.mongodb;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    public static void main( String[] args ) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        String connectionString = "mongodb+srv://Jeffrey:" + System.getenv("PASSWORD") + "@freecluster.h5y8j.mongodb.net/FreeCluster?retryWrites=true&w=majority";
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            //printDatabases(mongoClient)
            createDocuments(mongoClient);
        }
    }

    private static void createDocuments(MongoClient mongoClient) {
        MongoCollection<Document> cookies = mongoClient.getDatabase("xmas").getCollection("cookies");
        Document doc = new Document("name","chocolate chips");
        cookies.insertOne(doc);
    }

    private static void printDatabases(MongoClient mongoClient) {
        List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList<>());
        dbDocuments.forEach(document -> System.out.println(document.toJson()));
    }
}
