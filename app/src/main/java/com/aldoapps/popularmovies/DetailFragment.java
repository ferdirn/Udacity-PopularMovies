package com.aldoapps.popularmovies;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aldoapps.popularmovies.adapter.CommentAdapter;
import com.aldoapps.popularmovies.adapter.TrailerAdapter;
import com.aldoapps.popularmovies.data.FlagPreference;
import com.aldoapps.popularmovies.data.MovieProvider;
import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;
import com.aldoapps.popularmovies.model.review.Review;
import com.aldoapps.popularmovies.model.review.ReviewResponse;
import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.aldoapps.popularmovies.model.trailer.TrailerResponse;
import com.aldoapps.popularmovies.tjerkw.slideexpandable.library.SlideExpandableListAdapter;
import com.aldoapps.popularmovies.util.MovieConst;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aldokelvianto on 03/09/2015.
 */
public class DetailFragment extends Fragment {

    @Bind(R.id.movie_poster) ImageView mPoster;
    @Bind(R.id.movie_year) TextView mYear;
    @Bind(R.id.movie_rating) TextView mRating;
    @Bind(R.id.movie_duration) TextView mDuration;
    @Bind(R.id.movie_summary) TextView mSummary;
    @Bind(R.id.mark_as_favorite) FloatingActionButton mFavorite;
    @Bind(R.id.trailer_list) RecyclerView mTrailerListView;
    @Bind(R.id.comment_list) ListView mCommentListView;
    @Bind(R.id.backdrop) ImageView mBackdrop;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.budget) TextView mBudget;
    @Bind(R.id.popularity) TextView mPopularity;

    // Containers
    @Bind(R.id.comment_container) View mCommentContainer;
    @Bind(R.id.trailer_container) View mTrailerContainer;

    private int mMovieId = 0;
    private MovieDetail mMovie = null;
    private TrailerAdapter mTrailerAdapter;
    private CommentAdapter mCommentAdapter;
    private List<Trailer> mTrailers = new ArrayList<>();
    private Call<MovieDetail> mCallMovieDetail;

    public static final String TAG = DetailFragment.class.getSimpleName();
    private List<Review> mComments = new ArrayList<>();
    private Call<TrailerResponse> mCallTrailerDetail;
    private Call<ReviewResponse> mCallComments;

    private ProgressDialog mProgressDialog;

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

        setHasOptionsMenu(true);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.fetch_movie_detail));
        mProgressDialog.show();
    }

    public void callMovieDetail(final int movieId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
        mCallMovieDetail = tmdbApi.getMovieDetail(movieId,
                getResources().getString(R.string.API_KEY));
        mCallMovieDetail.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                MovieDetail movie = response.body();
                mMovie = movie;

                Picasso.with(getContext())
                        .load(UrlUtil.generatePosterUrl(movie.getPosterPath()))
                        .into(mPoster);

                Picasso.with(getContext())
                        .load(UrlUtil.generateBackdropUrl(movie.getBackdropPath()))
                        .into(mBackdrop);

                mCollapsingToolbar.setTitle(mMovie.getTitle());
                mYear.setText(movie.getReleaseYear());
                mDuration.setText(getString(R.string.duration, movie.getRuntime()));
                mSummary.setText(movie.getOverview());
                mRating.setText(String.valueOf(movie.getVoteAverage()));
                Formatter formatter = new Formatter();
                mPopularity.setText(formatter.format("%.2f", movie.getPopularity()).toString());
                if (movie.getBudget() == 0) {
                    mBudget.setVisibility(View.GONE);
                } else {
                    DecimalFormat customDF = new DecimalFormat("#,###");
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    symbols.setGroupingSeparator('.');
                    customDF.setDecimalFormatSymbols(symbols);
                    mBudget.setText(customDF.format(movie.getBudget()));
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

            }
        });

        mCallTrailerDetail = tmdbApi.getMovieTrailers(movieId,
                getString(R.string.API_KEY));

        mCallTrailerDetail.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                mTrailers.clear();
                for (Trailer trailer : response.body().getResults()) {
                    mTrailers.add(trailer);
                    mTrailerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });

        mCallComments = tmdbApi.getMovieReviews(movieId,
                getString(R.string.API_KEY));

        mCallComments.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                mComments.clear();
                for (Review review : response.body().getResults()) {
                    mComments.add(review);
                }
                mCommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        checkUserPreferences();
        checkFabStatus();

        mTrailerAdapter = new TrailerAdapter(mTrailers);
        mCommentAdapter = new CommentAdapter(getActivity(), mComments);

        ((DetailActivity) getActivity()).setSupportActionBar(mToolbar);
        ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCommentListView.setAdapter(mCommentAdapter);
        mCommentListView.setAdapter(new SlideExpandableListAdapter(
                mCommentAdapter,
                R.id.author_text,
                R.id.expandable
        ));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mTrailerListView.setAdapter(mTrailerAdapter);
        mTrailerListView.setLayoutManager(layoutManager);

        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFavoriteMovieStatus();
            }
        });

        // if we already querying movie runtime, use restored bundle
        if(savedInstanceState != null){
//            mDuration.setText(savedInstanceState.getString(MovieConst.MOVIE_RUNTIME));
        }else{
            switch (FlagPreference.getFlag(getContext())){
                case FlagPreference.SORT_BY_FAVORITE:
                    loadMovieFromDb();
                    break;
                case FlagPreference.SORT_BY_HIGHEST_RATED:
                    callMovieDetail(mMovieId);
                    break;
                case FlagPreference.SORT_BY_POPULARITY:
                    callMovieDetail(mMovieId);
                    break;
            }
        }

        return view;
    }

    private void checkFabStatus() {
        MovieProvider movieProvider = new MovieProvider(getContext());
        if(movieProvider.isMovieExistOnDb(mMovieId)){
            rotateFabDown();
        }else{
            rotateFabUp();
        }
        movieProvider.close();
    }

    private void changeFavoriteMovieStatus() {
        MovieProvider movieProvider = new MovieProvider(getContext());
        if(movieProvider.isMovieExistOnDb(mMovieId)){
            removeFavoriteMovie();
            Toast.makeText(getContext(), getString(R.string.removed_from_favorite), Toast.LENGTH_SHORT).show();
            getActivity().finish();
            rotateFabUp();
        }else{
            saveFavoriteMovie();
            rotateFabDown();
        }
        movieProvider.close();
    }

    private void removeFavoriteMovie() {
        MovieProvider movieProvider = new MovieProvider(getContext());
        movieProvider.deleteMovie(mMovieId);
        movieProvider.close();
    }

    private void rotateFabUp(){
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        final RotateAnimation animRotate = new RotateAnimation(0.0f, 0.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animRotate.setDuration(500);
        animRotate.setFillAfter(true);
        animSet.addAnimation(animRotate);

        mFavorite.startAnimation(animSet);
    }

    private void rotateFabDown(){
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        final RotateAnimation animRotate = new RotateAnimation(0.0f, -180.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animRotate.setDuration(500);
        animRotate.setFillAfter(true);
        animSet.addAnimation(animRotate);

        mFavorite.startAnimation(animSet);
    }



    private void checkUserPreferences() {
        switch (FlagPreference.getFlag(getContext())){
            case FlagPreference.SORT_BY_FAVORITE:
                mTrailerContainer.setVisibility(View.GONE);
                mCommentContainer.setVisibility(View.GONE);
                break;
            case FlagPreference.SORT_BY_HIGHEST_RATED:
                mTrailerContainer.setVisibility(View.VISIBLE);
                mCommentContainer.setVisibility(View.VISIBLE);
                break;
            case FlagPreference.SORT_BY_POPULARITY:
                mTrailerContainer.setVisibility(View.VISIBLE);
                mCommentContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void loadMovieFromDb(){
        MovieProvider movieProvider = new MovieProvider(getContext());
        MovieDetail movie = movieProvider.getMovieDetail(mMovieId);
        movieProvider.close();

        String posterPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + MovieConst.DIR_NAME + "/" + String.valueOf(movie.getId()) + ".png";

        String backdropPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + MovieConst.DIR_NAME + MovieConst.DIR_NAME_BACKDROP + "/"
                + String.valueOf(movie.getId()) + ".png";

        mPoster.setImageDrawable(Drawable.createFromPath(posterPath));
        mBackdrop.setImageDrawable(Drawable.createFromPath(backdropPath));

        mCollapsingToolbar.setTitle(movie.getTitle());
        mYear.setText(movie.getReleaseYear());
        mDuration.setText(getString(R.string.duration, movie.getRuntime()));
        mSummary.setText(movie.getOverview());
        mRating.setText(String.valueOf(movie.getVoteAverage()));
        Formatter formatter = new Formatter();
        mPopularity.setText(formatter.format("%.2f", movie.getPopularity()).toString());
        if (movie.getBudget() == 0) {
            mBudget.setVisibility(View.GONE);
        } else {
            DecimalFormat customDF = new DecimalFormat("#,###");
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            customDF.setDecimalFormatSymbols(symbols);
            mBudget.setText(customDF.format(movie.getBudget()));
        }
    }

    private void saveFavoriteMovie() {
        String posterPath = savePosterToStorage();
        String backdropPath = saveBackdropToStorage();
        if(posterPath != null && backdropPath != null){
            Toast.makeText(getActivity(), getString(R.string.saved_to_favorite), Toast.LENGTH_SHORT).show();
        }

        MovieProvider movieProvider = new MovieProvider(getActivity());
        movieProvider.insertMovie(mMovie, posterPath, backdropPath);
        movieProvider.close();
    }

    private String saveBackdropToStorage() {
        Bitmap bitmap;
        OutputStream outputStream;

        bitmap = ((BitmapDrawable) mBackdrop.getDrawable()).getBitmap();

        File filePath = Environment.getExternalStorageDirectory();
        File backdropDir = new File(filePath.getAbsolutePath()
                + MovieConst.DIR_NAME
                + MovieConst.DIR_NAME_BACKDROP);
        if(!backdropDir.exists()){
            backdropDir.mkdir();
        }

        File backdropFile = new File(backdropDir, String.valueOf(mMovie.getId()) + ".png");

        boolean isSaveSuccessful;

        try{
            outputStream = new FileOutputStream(backdropFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, MovieConst.COMPRESSION_RATE, outputStream);
            outputStream.flush();
            outputStream.close();
            isSaveSuccessful = true;
        }catch (IOException e){
            isSaveSuccessful = false;
            Log.e(PMApplication.TAG, e.getMessage());
        }

        if(isSaveSuccessful){
            String completePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + MovieConst.DIR_NAME + MovieConst.DIR_NAME_BACKDROP
                    + "/" + String.valueOf(mMovie.getId()) + ".png";
            return completePath;
        }

        return null;
    }

    private String savePosterToStorage() {
        Bitmap bitmap;
        OutputStream outputStream;

        bitmap = ((BitmapDrawable) mPoster.getDrawable()).getBitmap();

        File filePath = Environment.getExternalStorageDirectory();

        File imageDir = new File(filePath.getAbsolutePath() + MovieConst.DIR_NAME);
        if(!imageDir.exists()){
            imageDir.mkdir();
        }

        File imageFile = new File(imageDir, String.valueOf(mMovie.getId()) + ".png");

        boolean isSaveSuccessful;

        try {
            outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, MovieConst.COMPRESSION_RATE, outputStream);
            outputStream.flush();
            outputStream.close();
            isSaveSuccessful = true;

            /**
             * I deliberately didn't broadcast Media Scanner
             * (So, user can see movie poster in Gallery)
             * Because I want to make it hard for user to access poster,
             * thus reducing the risk of user deleting posters.
             */
        } catch (IOException e) {
            isSaveSuccessful = false;
            Log.e(PMApplication.TAG, e.getMessage());
        }

        if(isSaveSuccessful){
            String completePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + MovieConst.DIR_NAME + "/" + String.valueOf(mMovie.getId()) + ".png";
            return completePath;
        }

        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString(MovieConst.MOVIE_RUNTIME, mMovieConst.getDuration());
    }

    @Override
    public void onPause() {
        /**
         * We need to cancel all queue, because sometimes onResponse is called
         * even after the activity is destroyed. Thus creating NPE
         * Good thing Retrofit 2 has .cancel() features
         */
        if(mCallMovieDetail != null){
            mCallMovieDetail.cancel();
        }

        if(mCallTrailerDetail != null){
            mCallTrailerDetail.cancel();
        }

        if(mCallComments != null){
            mCallComments.cancel();
        }


        super.onPause();
    }
}
