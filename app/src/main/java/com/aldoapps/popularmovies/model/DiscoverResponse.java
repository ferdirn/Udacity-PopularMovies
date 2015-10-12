package com.aldoapps.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DiscoverResponse implements Parcelable {
    public int page;
    public List<Movie> results;
    public int totalPages;
    public int totalResults;

    protected DiscoverResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(Movie.CREATOR);
        totalPages = in.readInt();
        totalResults = in.readInt();
    }

    public static final Creator<DiscoverResponse> CREATOR = new Creator<DiscoverResponse>() {
        @Override
        public DiscoverResponse createFromParcel(Parcel in) {
            return new DiscoverResponse(in);
        }

        @Override
        public DiscoverResponse[] newArray(int size) {
            return new DiscoverResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedList(results);
        dest.writeInt(totalPages);
        dest.writeInt(totalResults);
    }
}