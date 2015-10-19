package com.aldoapps.popularmovies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.aldoapps.popularmovies.model.discover.Movie;
import com.aldoapps.popularmovies.model.review.Review;
import com.aldoapps.popularmovies.model.review.ReviewResponse;
import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.aldoapps.popularmovies.model.trailer.TrailerResponse;
import com.aldoapps.popularmovies.util.MovieConst;
import com.aldoapps.popularmovies.util.TrailerDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.registerTypeAdapter(Trailer.class, new TrailerDeserializer());
//            Gson gson = gsonBuilder.create();
//
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MovieConst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TmdbApi tmdbApi = retrofit.create(TmdbApi.class);

            Call<ReviewResponse> call = tmdbApi
                    .getMovieReviews(49026, getResources().getString(R.string.API_KEY));

            Log.d("asdf", "attempting request");

            call.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Response<ReviewResponse> response, Retrofit retrofit) {
                    Log.d("asdf", "comment " + response.body().getResults().get(0).getContent());
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("asdf", "failure happens" + t.getMessage());

                }
            });

//            Call<DiscoverResponse> call = null;
//
//            switch (sortBy){
//                case MovieConst.SORT_BY_HIGHEST_RATED_DESC:
//                    call = tmdbApi.discoverMovies(sortBy,
//                            getResources().getString(R.string.API_KEY),
//                            MovieConst.VOTE_AVERAGE_VALUE,
//                            MovieConst.VOTE_COUNT_VALUE);
//                    break;
//                case MovieConst.SORT_BY_POPULARITY_DESC:
//                    call = tmdbApi.discoverMovies(sortBy,
//                            getResources().getString(R.string.API_KEY)
//                            );
//                    break;
//            }
//
//            if (call != null) {
//                mMovieList.clear();
//                call.enqueue(new Callback<DiscoverResponse>() {
//                    @Override
//                    public void onResponse(Response<DiscoverResponse> response, Retrofit retrofit) {
//                        mMovieList.addAll(response.body().getMovies());
//                        mAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Toast.makeText(getActivity(), "Fail to fetch data ", Toast.LENGTH_SHORT);
//                    }
//                });
//            }
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
