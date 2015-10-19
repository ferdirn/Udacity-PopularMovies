package com.aldoapps.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;
import com.aldoapps.popularmovies.util.MovieConst;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.bumptech.glide.Glide;

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
//            mDuration.setText(savedInstanceState.getString(MovieConst.MOVIE_RUNTIME));
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
}
