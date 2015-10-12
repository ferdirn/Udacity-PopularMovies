package com.aldoapps.popularmovies.util;

import com.aldoapps.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by user on 12/10/2015.
 */
public class MovieDeserializer implements JsonDeserializer<Movie> {

    @Override
    public Movie deserialize(JsonElement json, Type typeOfT,
                             JsonDeserializationContext context) throws JsonParseException {
        JsonElement content = json.getAsJsonObject().get("results");
        return new Gson().fromJson(content, Movie.class);
    }
}
