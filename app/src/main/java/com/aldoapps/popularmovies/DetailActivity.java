package com.aldoapps.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.aldoapps.popularmovies.util.MovieConst;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aldokelvianto on 03/09/2015.
 */
public class DetailActivity extends AppCompatActivity implements DetailFragment.DetailCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if(getIntent() != null){
            int movieId = getIntent().getIntExtra(MovieConst.BUNDLE_KEY_MOVIE_ID, MovieConst.DEFAULT_VALUE);
            startDetailFragment(movieId);
        }
    }

    private void startDetailFragment(int movieId){
        DetailFragment fragment = (DetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);

        if(fragment == null){
            fragment = DetailFragment.newInstance(movieId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, DetailFragment.TAG)
                    .commit();
        }

    }

    @Override
    public void updateUI() {
        onBackPressed();
    }
}
