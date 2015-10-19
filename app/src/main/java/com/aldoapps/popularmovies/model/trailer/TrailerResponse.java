package com.aldoapps.popularmovies.model.trailer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailerResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<Trailer> results = new ArrayList<Trailer>();

    /**
     * No args constructor for use in serialization
     *
     */
    public TrailerResponse() {
    }

    /**
     *
     * @param id
     * @param results
     */
    public TrailerResponse(int id, List<Trailer> results) {
        this.id = id;
        this.results = results;
    }

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Trailer> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Trailer> results) {
        this.results = results;
    }

}