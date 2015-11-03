package com.aldoapps.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
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

import com.aldoapps.popularmovies.adapter.CommentAdapter;
import com.aldoapps.popularmovies.adapter.TrailerAdapter;
import com.aldoapps.popularmovies.data.PaperMovie;
import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;
import com.aldoapps.popularmovies.model.review.Review;
import com.aldoapps.popularmovies.model.review.ReviewResponse;
import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.aldoapps.popularmovies.model.trailer.TrailerResponse;
import com.aldoapps.popularmovies.tjerkw.slideexpandable.library.SlideExpandableListAdapter;
import com.aldoapps.popularmovies.util.MovieConst;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by user on 03/09/2015.
 */
public class DetailFragment extends Fragment {

    private static final String ALDO_DB = "aldoapps.aldo";
    @Bind(R.id.movie_poster) ImageView mPoster;
    @Bind(R.id.movie_title) TextView mName;
    @Bind(R.id.movie_year) TextView mYear;
    @Bind(R.id.movie_rating) TextView mRating;
    @Bind(R.id.movie_duration) TextView mDuration;
    @Bind(R.id.movie_summary) TextView mSummary;
    @Bind(R.id.mark_as_favorite) Button mFavorite;
    @Bind(R.id.trailer_list) ListView mTrailerListView;
    @Bind(R.id.comment_list) ListView mCommentListView;
    @Bind(R.id.movie_poster_two) ImageView mMoviePosterTwo;

    private MovieConst mMovieConst;
    private int mMovieId = 0;
    private MovieDetail mMovie = null;
    private TrailerAdapter mTrailerAdapter;
    private CommentAdapter mCommentAdapter;
    private List<Trailer> mTrailers = new ArrayList<>();

    public static final String TAG = DetailFragment.class.getSimpleName();
    private List<Review> mComments = new ArrayList<>();

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

        Paper.init(getActivity().getApplicationContext());
    }

    public void enqueueFetchMovieDetail(final int movieId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
        Call<MovieDetail> callMovieDetail = tmdbApi.getMovieDetail(movieId,
                getResources().getString(R.string.API_KEY));

        callMovieDetail.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Response<MovieDetail> response, Retrofit retrofit) {
                MovieDetail movie = response.body();
                mMovie = movie;
                Glide.with(getActivity().getApplicationContext())
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

        Call<TrailerResponse> callTrailerDetail = tmdbApi.getMovieTrailers(movieId,
                getString(R.string.API_KEY));

        callTrailerDetail.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Response<TrailerResponse> response, Retrofit retrofit) {
                mTrailers.clear();
                for(Trailer trailer : response.body().getResults()){
                    mTrailers.add(trailer);
                    mTrailerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        Call<ReviewResponse> callComments = tmdbApi.getMovieReviews(movieId,
                getString(R.string.API_KEY));

        callComments.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Response<ReviewResponse> response, Retrofit retrofit) {
                mComments.clear();
                for(Review review : response.body().getResults()){
                    mComments.add(review);
               }
                mCommentAdapter.notifyDataSetChanged();
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
        new ImageTask().execute(mMovie.getPosterPath());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString(MovieConst.MOVIE_RUNTIME, mMovieConst.getDuration());
    }

    class ImageTask extends AsyncTask<String, Integer, FutureTarget<File>>{

        @Override
        protected FutureTarget<File> doInBackground(String... params) {
            return Glide.with(getActivity())
                    .load(UrlUtil.generatePosterUrl(params[0]))
                    .downloadOnly(100, 100);
        }

        @Override
        protected void onPostExecute(FutureTarget<File> fileFutureTarget) {
            super.onPostExecute(fileFutureTarget);

            new FutureTask().execute(fileFutureTarget);
        }
    }

    class FutureTask extends AsyncTask<FutureTarget<File>, Integer, File>{

        @Override
        protected File doInBackground(FutureTarget<File>... future) {
            try {
                return future[0].get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            try {
                Log.d("asdf", "current movie id " + mMovieId + " name " + mMovie.getTitle()
                        + "cached canonical path : " + file.getCanonicalPath());

                PaperMovie thisMovie = new PaperMovie();
                thisMovie.setMovieId(String.valueOf(mMovieId));
                thisMovie.setMovieName(mMovie.getTitle());
                thisMovie.setPosterFilePath(file.getCanonicalPath());

                if(Paper.book().read(String.valueOf(mMovieId)) == null){
                    Log.d("asdf", "paper is null");
                    // write to database
                    Paper.book().write(String.valueOf(mMovieId), thisMovie);
                }else{
                    Log.d("asdf", "paper is NOT null");

                    Glide.with(getActivity())
                            .load(((PaperMovie) Paper.book()
                                    .read(String.valueOf(mMovieId)))
                                    .getPosterFilePath())
                            .into(mMoviePosterTwo);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
