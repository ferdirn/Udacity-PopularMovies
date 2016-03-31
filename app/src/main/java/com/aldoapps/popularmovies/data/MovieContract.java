package com.aldoapps.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.DateFormat;

/**
 * Created by user on 31/10/2015.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.aldoapps.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        // _ID is for sqlite auto increment
        public static final String COL0_MOVIE_ID = "movie_id"; // id from tmdb
        public static final String COL1_POSTER = "poster";
        public static final String COL2_BACKDROP = "backdrop";
        public static final String COL3_TITLE = "title";
        public static final String COL4_YEAR = "year";   // INT
        public static final String COL5_RUNTIME = "runtime"; // INT runtime
        public static final String COL6_VOTE_AVERAGE = "vote_average"; // vote average
        public static final String COL7_VOTE_COUNT = "vote_count"; // INT
        public static final String COL8_POPULARITY = "popularity";
        public static final String COL9_TAGLINE = "tagline";
        public static final String COL10_BUDGET = "budget";
        public static final String COL11_OVERVIEW = "overview";

        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
