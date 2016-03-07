package com.aldoapps.popularmovies;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aldoapps.popularmovies.adapter.CommentAdapter;
import com.aldoapps.popularmovies.adapter.TrailerAdapter;
import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;
import com.aldoapps.popularmovies.model.review.Review;
import com.aldoapps.popularmovies.model.review.ReviewResponse;
import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.aldoapps.popularmovies.model.trailer.TrailerResponse;
import com.aldoapps.popularmovies.tjerkw.slideexpandable.library.SlideExpandableListAdapter;
import com.aldoapps.popularmovies.util.MovieConst;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    @Bind(R.id.mark_as_favorite) Button mFavorite;
    @Bind(R.id.trailer_list) ListView mTrailerListView;
    @Bind(R.id.comment_list) ListView mCommentListView;

    private MovieConst mMovieConst;
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

        mTrailerAdapter = new TrailerAdapter(getActivity(), mTrailers);
        mCommentAdapter = new CommentAdapter(getActivity(), mComments);
    }

    public void enqueueFetchMovieDetail(final int movieId){
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
                String completePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + MovieConst.DIR_NAME + "/" + String.valueOf(mMovie.getId()) + ".png";
                File file = new File(completePath, String.valueOf(mMovie.getId()) + ".png");
//                mPoster.setImageDrawable(Drawable.createFromPath(file.getAbsolutePath()));
                mPoster.setImageDrawable(Drawable.createFromPath(completePath));
//                Glide.with(getActivity())
//                        .load(UrlUtil.generatePosterUrl(movie.getPosterPath()))
//                        .into(mPoster);

                mName.setText(movie.getTitle());
                mYear.setText(movie.getReleaseYear());
                mDuration.setText(String.valueOf(movie.getRuntime()));
                mSummary.setText(movie.getOverview());
                mRating.setText(String.valueOf(movie.getVoteAverage()) + " / 10");
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
                for(Review review : response.body().getResults()){
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

        mCommentAdapter.notifyDataSetChanged();
        mCommentListView.setAdapter(mCommentAdapter);
        mCommentListView.setAdapter(new SlideExpandableListAdapter(
                mCommentAdapter,
                R.id.author_text,
                R.id.expandable
        ));

        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavoriteMovieTask();
            }
        });

        mTrailerListView.setAdapter(mTrailerAdapter);
        mTrailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(UrlUtil.watchYoutubeVideo(mTrailerAdapter.getItem(position).getKey()));
            }
        });

        // if we already querying movie runtime, use restored bundle
        if(savedInstanceState != null){
//            mDuration.setText(savedInstanceState.getString(MovieConst.MOVIE_RUNTIME));
        }else{
            enqueueFetchMovieDetail(mMovieId);
        }

        return view;
    }

    private void saveFavoriteMovieTask() {
        Bitmap bitmap;
        OutputStream outputStream;

//        bitmap = ((BitmapDrawable) mPoster.getDrawable()).getBitmap();
        bitmap = ((GlideBitmapDrawable) mPoster.getDrawable()).getBitmap();

        File filePath = Environment.getExternalStorageDirectory();

        File imageDir = new File(filePath.getAbsolutePath() + MovieConst.DIR_NAME);
        if(!imageDir.exists()){
            imageDir.mkdir();
        }
        File imageFile = new File(imageDir, String.valueOf(mMovie.getId()) + ".png");

        try {
            outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("asdf", "error karena " + e.getMessage());
        }

//        MovieProvider movieProvider = new MovieProvider(getActivity());
//        movieProvider.insertMovie(mMovie);
//        movieProvider.close();
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
