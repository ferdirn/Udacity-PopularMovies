package com.aldoapps.popularmovies.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;
import com.aldoapps.popularmovies.data.MovieContract.*;

/**
 * Created by aldokelvianto on 3/20/16.
 */
public class MovieDetailCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MovieDetailCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MovieDetail getMovieDetail(){
        MovieDetail movieDetail = new MovieDetail();

        movieDetail.setId(getInt(getColumnIndex(MovieEntry.COL0_MOVIE_ID)));
        movieDetail.setPosterPath(getString(getColumnIndex(MovieEntry.COL1_POSTER)));
        movieDetail.setBackdropPath(getString(getColumnIndex(MovieEntry.COL2_BACKDROP)));
        movieDetail.setTitle(getString(getColumnIndex(MovieEntry.COL3_TITLE)));
        movieDetail.setReleaseDate(getString(getColumnIndex(MovieEntry.COL4_YEAR)));
        movieDetail.setRuntime(getInt(getColumnIndex(MovieEntry.COL5_RUNTIME)));
        movieDetail.setVoteAverage(getDouble(getColumnIndex(MovieEntry.COL6_VOTE_AVERAGE)));
        movieDetail.setVoteCount(getInt(getColumnIndex(MovieEntry.COL7_VOTE_COUNT)));
        movieDetail.setPopularity(getDouble(getColumnIndex(MovieEntry.COL8_POPULARITY)));
        movieDetail.setTagline(getString(getColumnIndex(MovieEntry.COL9_TAGLINE)));
        movieDetail.setBudget(getInt(getColumnIndex(MovieEntry.COL10_BUDGET)));
        movieDetail.setOverview(getString(getColumnIndex(MovieEntry.COL11_OVERVIEW)));

        return movieDetail;
    }
}
