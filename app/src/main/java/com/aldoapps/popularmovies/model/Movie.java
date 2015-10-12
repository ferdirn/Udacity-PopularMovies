package com.aldoapps.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public int id;
    public String overview;
    public String release_date;
    public String poster_path;
    public float popularity;
    public String title;
    public float vote_average;
    public int vote_count;


    public Movie(int id, String overview, String release_date, String poster_path, long popularity, String title, long vote_average, int vote_count) {

        this.id = id;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeFloat(popularity);
        dest.writeString(title);
        dest.writeFloat(vote_average);
        dest.writeInt(vote_count);
    }

    public Movie(Parcel parcel) {
        this.id = parcel.readInt();
        this.overview = parcel.readString();
        this.release_date = parcel.readString();
        this.poster_path = parcel.readString();
        this.popularity = parcel.readFloat();
        this.title = parcel.readString();
        this.vote_average = parcel.readFloat();
        this.vote_count = parcel.readInt();

    }


    public static Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}