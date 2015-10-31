package com.aldoapps.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by user on 31/10/2015.
 */
public class MovieContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_MOVIE = "movie";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_TRAILERS = "trailers";
        public static final String COLUMN_REVIEWS = "reviews";

    }
}
