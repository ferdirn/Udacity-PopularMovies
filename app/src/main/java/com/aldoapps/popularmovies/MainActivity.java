package com.aldoapps.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.aldoapps.popularmovies.util.MovieConst;

public class MainActivity extends SingleFragmentActivity implements MainFragment.PosterCallback {

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onMoviePosterClicked(int movieId) {
        if(findViewById(R.id.movie_detail_container) != null){
            DetailFragment detailFragment = DetailFragment.newInstance(movieId, true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, detailFragment)
                    .commit();
        }else{
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(MovieConst.BUNDLE_KEY_MOVIE_ID, movieId);
            startActivity(intent);
        }
    }
}
