package com.aldoapps.popularmovies.data;

/**
 * Created by user on 31/10/2015.
 */
public class PaperMovie {

    private String movieId;

    private String movieName;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    private String posterFilePath;

    public String getPosterFilePath() {
        return posterFilePath;
    }

    public PaperMovie(){}

    public void setPosterFilePath(String posterFilePath) {
        this.posterFilePath = posterFilePath;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

}
