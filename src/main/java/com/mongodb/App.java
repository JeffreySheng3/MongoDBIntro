package com.mongodb;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

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
            updateDocuments(cookies);
            findDocuments(cookies);
        }
    }

    private static void findDocuments(MongoCollection<Document> cookies) {
        System.out.println("Find Operations");
        List<Document> lowCaloriesCookies = cookies.find(lte("calories", 500)).into(new ArrayList<>());
        lowCaloriesCookies.forEach(d -> System.out.println(d.toJson()));
    }

    private static void updateDocuments(MongoCollection<Document> cookies) {
        System.out.println("Update Operations");
        Random random = new Random();
        cookies.updateMany(new Document(), set("calories", random.nextInt(1000)));

        List<Document> cookiesList = cookies.find().into(new ArrayList<>());
        cookiesList.forEach(c -> {
            Object id = c.get("_id");
            Document cookie = cookies.findOneAndUpdate(new Document("_id", id), set("calories", random.nextInt(1000)));
//            System.out.println(cookie.toJson());
        });
    }

    private static void deleteDocuments(MongoCollection<Document> cookies) {
        System.out.println("Delete Operations");
//        cookies.deleteMany(in("color", List.of("blue", "orange")));
        cookies.deleteMany(new Document());
    }

    private static void createDocuments(MongoCollection<Document> cookies) {
        System.out.println("Create Operations");
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
