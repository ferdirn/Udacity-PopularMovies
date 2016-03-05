package com.aldoapps.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.aldoapps.popularmovies.data.MovieContract;
import com.aldoapps.popularmovies.data.MovieDbHelper;
import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;

/**
 * Created by aldokelvianto on 3/5/16.
 */
public class TestDb extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateDb() throws Throwable {
        // delete first, to make sure we're doing clean test
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);

        SQLiteDatabase db = new MovieDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertMovieDb() throws Throwable {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);

        MovieDetail movie = new MovieDetail();
        movie.setId(123);
        movie.setPosterPath("/123");
        movie.setBackdropPath("/1234");
        movie.setTitle("123 the movie");
        movie.setReleaseDate("1234-7-12");
        movie.setRuntime(90);
        movie.setVoteAverage(7.1);
        movie.setVoteCount(200);
        movie.setTagline("amazing movie");
        movie.setOverview("the quick brown fox jumps over lazy dog");

        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COL0_MOVIE_ID, movie.getId());
        values.put(MovieContract.MovieEntry.COL1_POSTER, movie.getPosterPath());
        values.put(MovieContract.MovieEntry.COL2_BACKDROP, movie.getBackdropPath());
        values.put(MovieContract.MovieEntry.COL3_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COL4_YEAR, movie.getReleaseYear());
        values.put(MovieContract.MovieEntry.COL5_RUNTIME, movie.getRuntime());
        values.put(MovieContract.MovieEntry.COL6_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieContract.MovieEntry.COL7_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieContract.MovieEntry.COL8_TAGLINE, movie.getTagline());
        values.put(MovieContract.MovieEntry.COL9_OVERVIEW, movie.getOverview());

        MovieDbHelper db = new MovieDbHelper(this.mContext);

        long hasil = db.getWritableDatabase().insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        assertEquals(true, (hasil != -1));
        db.close();
    }

    public void testGetAllMovies() throws Throwable {
        MovieDbHelper helper = new MovieDbHelper(this.mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_NAME, null, null, null, null, null, null);
        assertNotNull(cursor);
    }

}
