package com.aldoapps.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aldoapps.popularmovies.data.MovieContract.MovieEntry;

import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;

import java.sql.SQLException;

/**
 * Created by aldokelvianto on 3/5/16.
 */
public class MovieProvider {
    private SQLiteDatabase mDatabase;
    private MovieDbHelper mDbHelper;

    public MovieProvider(Context context){
        mDbHelper = new MovieDbHelper(context);

        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void open() throws SQLException{
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close(){
        mDbHelper.close();
        mDatabase.close();
    }

    public void insertMovie(MovieDetail movie, String posterPath, String backdropPath){
        ContentValues values = new ContentValues();
        values.put(MovieEntry.COL0_MOVIE_ID, movie.getId());
        values.put(MovieEntry.COL1_POSTER, posterPath);
        values.put(MovieEntry.COL2_BACKDROP, backdropPath);
        values.put(MovieEntry.COL3_TITLE, movie.getTitle());
        values.put(MovieEntry.COL4_YEAR, movie.getReleaseYear());
        values.put(MovieEntry.COL5_RUNTIME, movie.getRuntime());
        values.put(MovieEntry.COL6_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieEntry.COL7_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieEntry.COL8_POPULARITY, movie.getPopularity());
        values.put(MovieEntry.COL9_TAGLINE, movie.getTagline());
        values.put(MovieEntry.COL10_BUDGET, movie.getBudget());
        values.put(MovieEntry.COL11_OVERVIEW, movie.getOverview());

        long hasil = mDatabase.insert(MovieEntry.TABLE_NAME, null, values);
        Log.d("asdf", "hasil id " + hasil);
    }

    public MovieDetail getMovie(int movieId){
        String whereClause = MovieEntry.COL0_MOVIE_ID + " = ?";
        Cursor movieCursor = mDatabase.query(MovieEntry.TABLE_NAME, null, whereClause,
                new String[]{ String.valueOf(movieId) },
                null, null, null); //293660
        return convertCursorToMovie(movieCursor);
    }

    private MovieDetail convertCursorToMovie(Cursor cursor) {
        MovieDetail movieDetail = new MovieDetail();
        cursor.moveToFirst();
        Log.d("asdf", "judul " + cursor.getString(4));
        Log.d("asdf", "poster path + backdrop " + cursor.getString(2));
        Log.d("asdf", "over view " + cursor.getString(10));

        movieDetail.setId(cursor.getInt(1));
        movieDetail.setPosterPath(cursor.getString(2));
        movieDetail.setBackdropPath(cursor.getString(3));
        movieDetail.setTitle(cursor.getString(4));
        movieDetail.setReleaseDate(cursor.getString(5));
        movieDetail.setRuntime(cursor.getInt(6));
        movieDetail.setVoteAverage(cursor.getDouble(7));
        movieDetail.setVoteCount(cursor.getInt(8));
        movieDetail.setPopularity(cursor.getDouble(9));
        movieDetail.setTagline(cursor.getString(10));
        movieDetail.setBudget(cursor.getInt(11));
        movieDetail.setOverview(cursor.getString(12));
        cursor.close();
        return movieDetail;
    }

}
