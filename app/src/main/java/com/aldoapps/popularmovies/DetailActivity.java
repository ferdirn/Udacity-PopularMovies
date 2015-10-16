package com.aldoapps.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aldoapps.popularmovies.util.MovieConst;

/**
 * Created by user on 03/09/2015.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        if(getIntent() != null){
            int movieId = getIntent().getIntExtra(MovieConst.KEY, MovieConst.DEFAULT_VALUE);
            Log.d("asdf", "get movie id on detail activity" + movieId);
            startDetailFragment(movieId);
        }
    }

    private void startDetailFragment(int movieId){
        DetailFragment fragment = (DetailFragment)
                getSupportFragmentManager().findFragmentByTag(DetailFragment.TAG);

        if(fragment == null){
            fragment = DetailFragment.newInstance(movieId);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, DetailFragment.TAG)
                .commit();
    }
}
