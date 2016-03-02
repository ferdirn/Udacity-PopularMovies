package com.aldoapps.popularmovies.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 31/10/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    final String CREATE_MOVIE_TABLE = "CREATE TABLE "
            + MovieContract.MovieEntry.TABLE_NAME + " ("
            + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, "
            + MovieContract.MovieEntry.Cols.POSTER + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.TITLE + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.BACKDROP + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.TITLE + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.YEAR + " INTEGER NOT NULL, "
            + MovieContract.MovieEntry.Cols.DURATION + " INTEGER NOT NULL, "
            + MovieContract.MovieEntry.Cols.VOTE_COUNT + " INTEGER NOT NULL, "
            + MovieContract.MovieEntry.Cols.TRAILER_LINK + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.RATING + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.SUMMARY + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.TAGLINE + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.REVIEW_AUTHOR + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.Cols.REVIEW_CONTENT + " TEXT NOT NULL "
            + " );";

    // Changing database schema requires you to change DB Version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "popularmovies.db";

    public MovieDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, MovieContract.MovieEntry.TABLE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
