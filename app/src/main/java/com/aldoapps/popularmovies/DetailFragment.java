package com.aldoapps.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldoapps.popularmovies.model.MovieDetail;
import com.aldoapps.popularmovies.util.MovieConst;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by user on 03/09/2015.
 */
public class DetailFragment extends Fragment {

    @Bind(R.id.movie_poster) ImageView mPoster;
    @Bind(R.id.movie_title) TextView mName;
    @Bind(R.id.movie_year) TextView mYear;
    @Bind(R.id.movie_rating) TextView mRating;
    @Bind(R.id.movie_duration) TextView mDuration;
    @Bind(R.id.movie_summary) TextView mSummary;
    @Bind(R.id.mark_as_favorite) Button mMarkAsFavorite;

    private MovieConst mMovieConst;
    private int mMovieId = 0;

    public static final String TAG = DetailFragment.class.getSimpleName();

    public static DetailFragment newInstance(int movieId){
        Bundle bundle = new Bundle();
        bundle.putInt(MovieConst.KEY, movieId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mMovieId = getArguments().getInt(MovieConst.KEY);
        }
    }

    public void enqueueFetchMovieDetail(final int movieId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
        Call<MovieDetail> call = tmdbApi.getMovieDetail(movieId,
                getResources().getString(R.string.API_KEY));

        Log.d("asdf", "movie id" + mMovieId);

        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Response<MovieDetail> response, Retrofit retrofit) {
                MovieDetail movie = response.body();
                Glide.with(getActivity())
                        .load(UrlUtil.generatePosterUrl(movie.getPosterPath()))
                        .into(mPoster);
                mName.setText(movie.getTitle());
                mYear.setText(movie.getReleaseDate());
                mDuration.setText(String.valueOf(movie.getRuntime()));
                mSummary.setText(movie.getOverview());
                mRating.setText(String.valueOf(movie.getVoteAverage()) + " / 10");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("asdf", "failure happens");

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        // if we already querying movie runtime, use restored bundle
        if(savedInstanceState != null){
            mDuration.setText(savedInstanceState.getString(MovieConst.MOVIE_RUNTIME));
        }else{
            enqueueFetchMovieDetail(mMovieId);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString(MovieConst.MOVIE_RUNTIME, mMovieConst.getDuration());
    }

    public String generateGetMovieDetailUrl(String movieId){

        Uri.Builder builder = Uri.parse(MovieConst.MOVIE_DETAIL_BASE_URL + movieId).buildUpon()
                .appendQueryParameter(MovieConst.API_KEY_PARAM,
                        getString(R.string.API_KEY));

        Uri uri = builder.build();

        return uri.toString();
    }

    public class FetchMovieDetail extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            setMovieRating(jsonString);
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();

            try {
                URL url = new URL(generateGetMovieDetailUrl(params[0]));
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

                if(sb.length() == 0){
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }
    }

    private void setMovieRating(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
//            mMovieConst.setDuration(String.valueOf(jsonObject.getInt(MovieConst.MOVIE_RUNTIME)) + " minutes");
//            mDuration.setText(mMovieConst.getDuration());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
