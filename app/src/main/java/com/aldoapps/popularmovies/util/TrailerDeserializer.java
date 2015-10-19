package com.aldoapps.popularmovies.util;

import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * IGNORE THIS CLASS.
 * This class supposedly used for extract/deserialize the
 * results only in tmdb API. However, I can't make it works
 * for now. So, you may ignore it.
 */
public class TrailerDeserializer implements JsonDeserializer<List<Trailer>> {

    private List<Trailer> mTrailers = new ArrayList<>();

    @Override
    public List<Trailer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject rootObject = json.getAsJsonObject();

        // Method 1. Using GSON Deserialization
        Trailer[] trailers = context.deserialize(rootObject.get("results"), Trailer[].class);
        Collections.addAll(mTrailers, trailers);

//        JsonArray resultArray = rootObject.get("results").getAsJsonArray();
//        for(JsonElement jsonElement : resultArray){
//            JsonObject currentJsonObject = jsonElement.getAsJsonObject();
//            Trailer trailer = new Trailer();
//            trailer.setId(currentJsonObject.get("id").getAsString());
//            trailer.setIso6391(currentJsonObject.get("iso_639_1").getAsString());
//            trailer.setKey(currentJsonObject.get("key").getAsString());
//            trailer.setName(currentJsonObject.get("name").getAsString());
//            trailer.setSite(currentJsonObject.get("site").getAsString());
//            trailer.setSize(currentJsonObject.get("size").getAsInt());
//            trailer.setType(currentJsonObject.get("type").getAsString());
//            System.out.println("trailer " + trailer.getKey());
//            mTrailers.add(trailer);
//        }

        return  mTrailers;
    }
}
