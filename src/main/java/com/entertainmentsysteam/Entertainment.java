package com.entertainmentsysteam;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.*;

public class Entertainment {

    public static void main(String[] obj) {

        String fileName = "assets.json";

        ClassLoader classLoader = new Entertainment().getClass().getClassLoader();

        try {

            JsonReader jsonReader = new JsonReader(new InputStreamReader(classLoader.getResourceAsStream(fileName), "UTF-8"));

            Gson gson = new GsonBuilder().create();
            JsonArray movieArray = new JsonArray();

            jsonReader.beginArray();

            while (jsonReader.hasNext()) {

                JsonElement jsonElement = gson.fromJson(jsonReader, JsonElement.class);

                if (jsonElement.getAsJsonObject().get("object_class").getAsString().equals("Movie")) {
                    JsonObject movie = new JsonObject();

                    movie.addProperty("id", jsonElement.getAsJsonObject().get("id").getAsString());
                    movie.addProperty("title", jsonElement.getAsJsonObject().get("title").getAsString());
                    movie.addProperty("production_year", jsonElement.getAsJsonObject().get("production_year").getAsString());

                    movieArray.add(movie);
                }

            }

            jsonReader.close();

            Writer writer = new FileWriter("output.json");
            gson.toJson(movieArray,JsonArray.class, writer);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
