package com.aldoapps.popularmovies;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.grid_view) GridView mGridView;

    private MoviePosterAdapter mAdapter;
    private List<Movie> mMovieList;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieList = new ArrayList<>();
        mMovieList.add(new Movie("http://lorempixel.com/400/200/cats",
                "Chappie", "asdf", "asdf", 1f, "asdf"));
        mMovieList.add(new Movie("http://lorempixel.com/200/300/cats",
                "Kucing", "asdf", "asdf", 1f, "asdf"));

        // HttpURLConnection is recommended HTTP Client for Android
        HttpURLConnection httpURLConnection = null;
        // Buffered Reader is used for read the byte you get from API
        BufferedReader reader = null;

        try {
            // use URL builder to prevent mistakes and convenience
            URL url = new URL(buildUrl());
            httpURLConnection = (HttpURLConnection) url.openConnection();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        mAdapter = new MoviePosterAdapter(getActivity(), mMovieList);
    }

    public String buildUrl(){
        final String BASE_URL = "http://image.tmdb.org/t/p";
        final String POSTER_SIZE_PARAM = "w185";
        // example final image URL
        // http://image.tmdb.org/t/p/w185/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg
        final String QUERY_PARAM = "query";


        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, "something")
                .build();

        return uri.toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                startActivity(intent);
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        return view;
    }
}
