package com.aldoapps.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private List<Movie> mMovieList = new ArrayList<>();;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executeMovieTask();

        generatePosterUrl("/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg");

        mAdapter = new MoviePosterAdapter(getActivity(), mMovieList);
    }

    private void executeMovieTask() {
        FetchMovieTask fetchMovieTask  = new FetchMovieTask();
        fetchMovieTask.execute(Constants.MovieValue.SORT_BY_HIGHEST_RATED_DESC);
    }

    public String generateDiscoverUrl(String sortByValue){

        final String DISCOVER_BASE_URL = "http://api.themoviedb.org/3/discover/movie";

        // optional param
        final String SORT_BY_PARAM = "sort_by";
        final String SORT_BY_VALUE = sortByValue;

        // required param
        final String API_KEY_PARAM = "api_key";
        final String API_KEY_VALUE = getResources().getString(R.string.API_KEY);

        Uri uri = Uri.parse(DISCOVER_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY_PARAM, SORT_BY_VALUE)
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .build();

        Log.d("asdf", uri.toString());

        return uri.toString();
    }

    public String generatePosterUrl(String posterPath){
        // example final image URL
        // http://image.tmdb.org/t/p/w185/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg
        final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
        final String POSTER_SIZE_PARAM = "/w185";

        Log.d("asdf", IMAGE_BASE_URL + POSTER_SIZE_PARAM + posterPath);
        return IMAGE_BASE_URL + POSTER_SIZE_PARAM + posterPath;
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
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String>{

        public final String TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected String doInBackground(String... params) {
            // for now, we didn't put any params
            // but later we will add params for sorting
            // most popular or highest rated
            if(params.length == 0){
                return null;
            }

            // HttpURLConnection is recommended HTTP Client for Android
            HttpURLConnection httpURLConnection = null;
            // Buffered Reader is used for read the byte you get from API
            BufferedReader reader = null;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                // use URL builder to prevent mistakes and convenience
                URL url = new URL(generateDiscoverUrl(params[0]));
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET"); // by default its GET though
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                // if response is null, exit
                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null){
                    stringBuilder.append(line).append(" \n");
                }

                // if string is empty, we can't parse it anyway
                if(stringBuilder.length() == 0){
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }finally {
                // disconnect and close
                // this stuff is auto generated by lint police
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("asdf", "hasil: " + s);
        }
    }
}
