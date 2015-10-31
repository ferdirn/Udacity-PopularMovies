package com.aldoapps.popularmovies.data;

/**
 * This class aggregate all Movie attributes
 * from different endpoints
 */
public class MovieComplete {
    private String id;
    private String poster;
    private String title;
    private String year;
    private String duration;
    private String rating;
    private String summary;
    private String trailers;
    private String reviews;

    public MovieComplete(){}

    public MovieComplete(String id, String poster, String title, String year,
                         String duration, String rating, String summary, String trailers, String reviews) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.rating = rating;
        this.summary = summary;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTrailers() {
        return trailers;
    }

    public void setTrailers(String trailers) {
        this.trailers = trailers;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }
}
