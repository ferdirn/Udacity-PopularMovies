package com.aldoapps.popularmovies.model;

import java.util.List;

/**
 * Created by user on 11/10/2015.
 */
public class DiscoverResponse {
    private int page;
    private List<Movie> results;
    private double total_pages;
    private double total_results;

    public DiscoverResponse(int page, List<Movie> results, double total_pages, double total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public double getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(double total_pages) {
        this.total_pages = total_pages;
    }

    public double getTotal_results() {
        return total_results;
    }

    public void setTotal_results(double total_results) {
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
