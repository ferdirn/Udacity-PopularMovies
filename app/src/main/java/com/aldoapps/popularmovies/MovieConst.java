package com.aldoapps.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 01/09/2015.
 */
public class MovieConst implements Parcelable{

    public static final String KEY = "movie";

    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    public static final String MOVIE_DETAIL_BASE_URL = BASE_URL + "movie/";
    public static final String DISCOVER_BASE_URL = BASE_URL + "discover/movie";

    public static final String SORT_BY_PARAM = "sort_by";
    public static final String API_KEY_PARAM = "api_key";
    public static final String VOTE_AVERAGE_PARAM = "vote_average.gte";
    public static final String VOTE_COUNT_PARAM = "vote_count.gte";

    public static final String SORT_BY_POPULARITY_DESC = "popularity.desc";
    public static final String SORT_BY_HIGHEST_RATED_DESC = "vote_average.desc";
    public static final String VOTE_AVERAGE_VALUE = "8";
    public static final String VOTE_COUNT_VALUE = "1000";

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String POSTER_SIZE_PARAM = "/w185";

    // JSON stuff
    public static final String MOVIE_ID = "id";
    public static final String MOVIE_ARRAY = "results";
    public static final String MOVIE_POSTER = "poster_path";
    public static final String MOVIE_ORIGINAL_TITLE = "original_title";
    public static final String MOVIE_RUNTIME = "runtime";
    public static final String MOVIE_OVERVIEW = "overview";
    public static final String MOVIE_RELEASE_DATE = "release_date";
    public static final String MOVIE_SCORE = "vote_average";

    private String id;
    private String posterUrl; // poster_path
    private String name; // original_title
    private String year; // release date, parse it
    private String duration; // get complete movie data from querying movie id
    private float score; // vote_average
    private String summary; // overview

    public MovieConst(String id, String posterUrl, String name, String year, String duration, float score, String summary) {
        this.id = id;
        this.posterUrl = posterUrl;
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.score = score;
        this.summary = summary;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.posterUrl);
        dest.writeString(this.name);
        dest.writeString(this.year);
        dest.writeString(this.duration);
        dest.writeFloat(this.score);
        dest.writeString(this.summary);
    }

    public MovieConst(Parcel in) {
        this.id = in.readString();
        this.posterUrl = in.readString();
        this.name = in.readString();
        this.year = in.readString();
        this.duration = in.readString();
        this.score = in.readFloat();
        this.summary = in.readString();
    }

    public static final Parcelable.Creator<MovieConst> CREATOR =
            new Parcelable.Creator<MovieConst>(){

                @Override
                public MovieConst createFromParcel(Parcel source) {
                    return new MovieConst(source);
                }

                @Override
                public MovieConst[] newArray(int size) {
                    return new MovieConst[size];
                }
            };

    public MovieConst() { }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
