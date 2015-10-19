package com.aldoapps.popularmovies.util;

import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17/10/2015.
 */
public class TrailerDeserializer implements JsonDeserializer<List<Trailer>> {

    private List<Trailer> mTrailers = new ArrayList<>();

    @Override
    public List<Trailer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject rootObject = json.getAsJsonObject();
//        JsonArray resultArray = rootObject.get("results").getAsJsonArray();
        System.out.println("attempting deserialization");

        Trailer[] trailers = context.deserialize(rootObject.get("results"), Trailer[].class);

        for (Trailer thisTrailer : trailers){
            mTrailers.add(thisTrailer);
        }

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
