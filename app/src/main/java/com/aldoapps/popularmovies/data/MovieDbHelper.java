package com.aldoapps.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 31/10/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    final String CREATE_MOVIE_TABLE = "CREATE TABLE "
            + MovieContract.MovieEntry.TABLE_NAME + " ("
            + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // id for sqlite db (auto-inc)
            + MovieContract.MovieEntry.COL0_MOVIE_ID + " INTEGER NOT NULL, " // id from tmdb
            + MovieContract.MovieEntry.COL1_POSTER + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.COL2_BACKDROP + " TEXT, "
            + MovieContract.MovieEntry.COL3_TITLE + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.COL4_YEAR + " TEXT NOT NULL, "
            + MovieContract.MovieEntry.COL5_RUNTIME + " INTEGER NOT NULL, "
            + MovieContract.MovieEntry.COL6_VOTE_AVERAGE + " REAL NOT NULL, "
            + MovieContract.MovieEntry.COL7_VOTE_COUNT + " INTEGER NOT NULL, "
            + MovieContract.MovieEntry.COL8_POPULARITY + " REAL NOT NULL, "
            + MovieContract.MovieEntry.COL9_TAGLINE + " TEXT, "
            + MovieContract.MovieEntry.COL10_BUDGET + " INTEGER, " // could be null
            + MovieContract.MovieEntry.COL11_OVERVIEW + " TEXT NOT NULL "
            + " );";

    // Changing database schema requires you to change DB Version
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "popularmovies.db";

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
