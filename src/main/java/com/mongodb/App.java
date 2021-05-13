package com.mongodb;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;

public class App
{
    public static void main( String[] args ) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        String connectionString = "mongodb+srv://Jeffrey:" + System.getenv("PASSWORD") + "@freecluster.h5y8j.mongodb.net/FreeCluster?retryWrites=true&w=majority";
        try(MongoClient mongoClient = MongoClients.create(connectionString)){
            MongoCollection<Document> cookies = mongoClient.getDatabase("xmas").getCollection("cookies");
            //printDatabases(mongoClient)
            deleteDocuments(cookies);
            createDocuments(cookies);
        }
    }

    private static void deleteDocuments(MongoCollection<Document> cookies) {
        cookies.deleteMany(in("color", List.of("blue", "orange")));
    }

    private static void createDocuments(MongoCollection<Document> cookies) {
        List<Document> cookiesList = new ArrayList<>();
        List<String> ingredients = List.of("flour", "eggs", "butter", "sugar", "red food coloring");

        for(int i = 0; i < 10; i++){
            cookiesList.add(new Document("cookie_id", i).append("color", "pink").append("ingredients", ingredients));
        }

        cookies.insertMany(cookiesList);
    }

    private static void printDatabases(MongoClient mongoClient) {
        List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList<>());
        dbDocuments.forEach(document -> System.out.println(document.toJson()));
    }
}
