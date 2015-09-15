package com.aldoapps.popularmovies;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private List<Movie> mMovieList = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mAdapter = new MoviePosterAdapter(getActivity(), mMovieList);

        executeMovieTask();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSortByDialogue();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSortByDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort_by);
        builder.setItems(R.array.sort_by_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        executeMovieTask(Movie.SORT_BY_POPULARITY_DESC);
                        break;
                    case 1:
                        executeMovieTask(Movie.SORT_BY_HIGHEST_RATED_DESC);
                        break;
                }
            }
        });
        builder.show();
    }

    private void executeMovieTask() {
        // default by popularity
        executeMovieTask(Movie.SORT_BY_POPULARITY_DESC);
    }

    private void executeMovieTask(String sortBy) {
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        switch (sortBy){
            case Movie.SORT_BY_HIGHEST_RATED_DESC:
                fetchMoviesTask.execute(Movie.SORT_BY_HIGHEST_RATED_DESC);
                break;
            case Movie.SORT_BY_POPULARITY_DESC:
                fetchMoviesTask.execute(Movie.SORT_BY_POPULARITY_DESC);
                break;
        }
    }

    public String generateDiscoverUrl(String sortByValue){

        Uri.Builder builder = Uri.parse(Movie.DISCOVER_BASE_URL).buildUpon()
                .appendQueryParameter(Movie.SORT_BY_PARAM, sortByValue)
                .appendQueryParameter(Movie.API_KEY_PARAM, getResources().getString(R.string.API_KEY));

        // sort by highest rated, require another two parameter
        // average votes and vote count (to make sure its not some
        // random movie with only a few people rate it 10) minimum of 1000 people
        if(sortByValue.equals(Movie.SORT_BY_HIGHEST_RATED_DESC)){
            builder.appendQueryParameter(Movie.VOTE_AVERAGE_PARAM, Movie.VOTE_AVERAGE_VALUE)
                    .appendQueryParameter(Movie.VOTE_COUNT_PARAM, Movie.VOTE_COUNT_VALUE);
        }

        Uri uri = builder.build();

        return uri.toString();
    }

    public String generatePosterUrl(String posterPath){
        // example final image URL
        // http://image.tmdb.org/t/p/w185/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg
        return Movie.IMAGE_BASE_URL + Movie.POSTER_SIZE_PARAM + posterPath;
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
                intent.putExtra(Movie.KEY, mMovieList.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String>{

        public final String TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mMovieList.clear();
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
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
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            convertJsonToMovies(jsonString);
            mAdapter.notifyDataSetChanged();

            mProgressDialog.hide();
        }
    }

    private void convertJsonToMovies(String jsonString) {
        try {
            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray movieList = rootObject.getJSONArray(Movie.MOVIE_ARRAY);

            for(int i = 0; i < movieList.length(); i++){
                Movie movie = new Movie();
                JSONObject movieObject = movieList.getJSONObject(i);
                movie.setId(movieObject.getString(Movie.MOVIE_ID));
                movie.setPosterUrl(generatePosterUrl(movieObject.getString(Movie.MOVIE_POSTER)));
                movie.setName(movieObject.getString(Movie.MOVIE_ORIGINAL_TITLE));
                movie.setScore(Float.parseFloat(movieObject.getString(Movie.MOVIE_SCORE)));
                movie.setYear(movieObject.getString(Movie.MOVIE_RELEASE_DATE));
                movie.setSummary(movieObject.getString(Movie.MOVIE_OVERVIEW));
                mMovieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
