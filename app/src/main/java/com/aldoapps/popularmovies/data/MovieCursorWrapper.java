package com.aldoapps.popularmovies.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.aldoapps.popularmovies.model.discover.Movie;

/**
 * Created by aldokelvianto on 3/20/16.
 */
public class MovieCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie(){
        Movie movie = new Movie();

        movie.setId(getInt(getColumnIndex(MovieContract.MovieEntry.COL0_MOVIE_ID)));
        movie.setPosterPath(getString(getColumnIndex(MovieContract.MovieEntry.COL1_POSTER)));
        movie.setTitle(getString(getColumnIndex(MovieContract.MovieEntry.COL3_TITLE)));

        return movie;
    }
}
