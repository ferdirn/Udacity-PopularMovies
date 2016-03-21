package com.aldoapps.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aldoapps.popularmovies.data.MovieContract.MovieEntry;

import com.aldoapps.popularmovies.model.discover.Movie;
import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldokelvianto on 3/5/16.
 * TODO: Use actual ContentProvider, for now just use SQLite implementation
 */
public class MovieProvider {
    private static MovieProvider sMovieProvider;

    private SQLiteDatabase mDatabase;

    public static MovieProvider get(Context context){
        if(sMovieProvider == null){
            sMovieProvider = new MovieProvider(context);
        }

        return sMovieProvider;
    }

    public MovieProvider(Context context){
        mDatabase = new MovieDbHelper(context).getWritableDatabase();
    }

    public boolean addMovie(MovieDetail movieDetail){
        ContentValues values = getMovieDetailContentValues(movieDetail);
        long result = mDatabase.insert(MovieEntry.TABLE_NAME, null, values);
        return result != -1;
    }

    public ContentValues getMovieDetailContentValues(MovieDetail movie){
        ContentValues values = new ContentValues();

        values.put(MovieEntry.COL0_MOVIE_ID, movie.getId());
        values.put(MovieEntry.COL1_POSTER, String.valueOf(movie.getId()));
        values.put(MovieEntry.COL2_BACKDROP, String.valueOf(movie.getId()));
        values.put(MovieEntry.COL3_TITLE, movie.getTitle());
        values.put(MovieEntry.COL4_YEAR, movie.getReleaseYear());
        values.put(MovieEntry.COL5_RUNTIME, movie.getRuntime());
        values.put(MovieEntry.COL6_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieEntry.COL7_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieEntry.COL8_POPULARITY, movie.getPopularity());
        values.put(MovieEntry.COL9_TAGLINE, movie.getTagline());
        values.put(MovieEntry.COL10_BUDGET, movie.getBudget());
        values.put(MovieEntry.COL11_OVERVIEW, movie.getOverview());

        return values;
    }

    public boolean isMovieExistOnDb(int movieId){
        MovieDetailCursorWrapper movieCursor = queryMovieDetail(movieId);

        boolean isMovieExist = false;

        if(movieCursor.moveToFirst()){
            isMovieExist = true;
        }

        movieCursor.close();
        return isMovieExist;
    }

    public void deleteMovie(int movieId){
        mDatabase.delete(MovieEntry.TABLE_NAME, MovieEntry.COL0_MOVIE_ID + " = ?", new String[]{
                String.valueOf(movieId)});
    }

    public MovieDetail getMovieDetail(int movieId){
        MovieDetailCursorWrapper movieCursor = queryMovieDetail(movieId);

        MovieDetail movieDetail = null;

        if(movieCursor.moveToFirst()){
            movieDetail = movieCursor.getMovieDetail();
        }

        Log.d("asdf", "is movie detail null? " + (movieDetail == null));

        movieCursor.close();
        return movieDetail;
    }

    public MovieDetailCursorWrapper queryMovieDetail(int movieId){
        String whereClause = MovieEntry.COL0_MOVIE_ID + " = ? ";
        Cursor cursor = mDatabase.query(MovieEntry.TABLE_NAME, null, whereClause,
                new String[]{ String.valueOf(movieId) },
                null, null, null); //293660

        return new MovieDetailCursorWrapper(cursor);
    }

    private MovieCursorWrapper queryAllMovie(){
        Cursor cursor = mDatabase.query(MovieEntry.TABLE_NAME, null,
                null, new String[]{}, null, null, null);
        return new MovieCursorWrapper(cursor);
    }

    public List<Movie> getAllMovie() {
        MovieCursorWrapper movieCursor = queryAllMovie();
        List<Movie> movieList = new ArrayList<>();

        if(movieCursor.moveToFirst()){
            while (!movieCursor.isAfterLast()){
                movieList.add(movieCursor.getMovie());
                movieCursor.moveToNext();
            }
        }
        return movieList;
    }

    @Override
    protected void finalize() throws Throwable {
        if(mDatabase != null){
            mDatabase.close();
        }

        super.finalize();
    }
}
