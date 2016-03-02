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
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final class Cols{
            public static final String POSTER = "poster";
            public static final String BACKDROP = "backdrop";
            public static final String TITLE = "title";
            public static final String YEAR = "year";   // INT
            public static final String DURATION = "duration"; // INT runtime
            public static final String RATING = "rating"; // vote average
            public static final String VOTE_COUNT = "votecount"; // INT
            public static final String TAGLINE = "tagline";
            public static final String SUMMARY = "summary";
            public static final String TRAILER_LINK = "trailer";
            public static final String REVIEW_CONTENT = "review_content";
            public static final String REVIEW_AUTHOR = "review_author";
        }

        public static final Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static String parseMovieYear(String releaseDate){
        return releaseDate.substring(0, 4);
    }
}
