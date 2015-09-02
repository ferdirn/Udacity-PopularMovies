package com.aldoapps.popularmovies;

/**
 * Created by user on 01/09/2015.
 */
public class Movie {
    private String posterUrl;
    private String name;
    private String year;
    private String duration;
    private float score;
    private String summary;

    public Movie(String posterUrl, String name, String year, String duration, float score, String summary) {
        this.posterUrl = posterUrl;
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.score = score;
        this.summary = summary;
    }

    public String getPosterUrl(){
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl){
        this.posterUrl = posterUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
