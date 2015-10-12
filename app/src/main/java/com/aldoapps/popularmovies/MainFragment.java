package com.aldoapps.popularmovies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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

import com.aldoapps.popularmovies.model.DiscoverResponse;
import com.aldoapps.popularmovies.model.Movie;
import com.aldoapps.popularmovies.model.Result;
import com.aldoapps.popularmovies.model.TmdbResponse;
import com.aldoapps.popularmovies.util.MovieDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.grid_view) GridView mGridView;

    private MoviePosterAdapter mAdapter;
    private List<Movie> mMovieList = new ArrayList<>();
    private String mSortByValue = "";

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new MoviePosterAdapter(getActivity(), mMovieList);
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
                switch (which) {
                    case 0:
                        mSortByValue = MovieConst.SORT_BY_POPULARITY_DESC;
                        executeMovieTask(MovieConst.SORT_BY_POPULARITY_DESC);
                        break;
                    case 1:
                        mSortByValue = MovieConst.SORT_BY_HIGHEST_RATED_DESC;
                        executeMovieTask(MovieConst.SORT_BY_HIGHEST_RATED_DESC);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        executeMovieTask();
    }

    private void executeMovieTask() {
        // default by popularity
        executeMovieTask(MovieConst.SORT_BY_POPULARITY_DESC);
    }

    private void executeMovieTask(String sortBy) {
        if(isNetworkAvailable()){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .registerTypeAdapter(Movie.class, new MovieDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MovieConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
            Call<DiscoverResponse> call = null;

            switch (sortBy){
                case MovieConst.SORT_BY_HIGHEST_RATED_DESC:
                    Log.d("asdf", "highest rated");
                    call = tmdbApi.discoverMovies(sortBy,
                            getResources().getString(R.string.API_KEY),
                            MovieConst.VOTE_AVERAGE_VALUE,
                            MovieConst.VOTE_COUNT_VALUE);
                    break;
                case MovieConst.SORT_BY_POPULARITY_DESC:
                    Log.d("asdf", "popularity");
                    call = tmdbApi.discoverMovies(sortBy,
                            getResources().getString(R.string.API_KEY)
                            );
                    break;
            }

            if (call != null) {
                mMovieList.clear();
                Log.d("asdf", "2");
                call.enqueue(new Callback<DiscoverResponse>() {
                    @Override
                    public void onResponse(Response<DiscoverResponse> response, Retrofit retrofit) {
                        Log.d("asdf", "is resonpse null? " + (response.body() == null));
                        Log.d("asdf", "is success? " + response.isSuccess());
                        Log.d("asdf", "base url " + retrofit.baseUrl().toString());

                        mMovieList.addAll(response.body().getResults());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getActivity(), "Fail to fetch data ", Toast.LENGTH_SHORT);
                    }
                });
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.no_internet_message)
                    .setTitle(R.string.no_internet_title)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    });
            builder.show();
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                intent.putExtra(MovieConst.KEY, mMovieList.get(position).getId());
                startActivity(intent);
            }
        });

        return view;
    }
}
