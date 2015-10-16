package com.aldoapps.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
            String movieId = getIntent().getStringExtra(MovieConst.KEY);
            startDetailFragment(movieId);
        }
    }

    private void startDetailFragment(String movieId){
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
