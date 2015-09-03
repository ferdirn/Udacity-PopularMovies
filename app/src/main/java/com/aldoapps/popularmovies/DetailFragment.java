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

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private Movie mMovie;

    public static final String TAG = DetailFragment.class.getSimpleName();

    public static DetailFragment newInstance(){
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        mMovie = new Movie("http://www.impawards.com/2015/posters/chappie_ver4.jpg",
                "Chappie", "2015", "120 minutes", 8.5f, "Something long");

        Glide.with(this).load(mMovie.getPosterUrl()).into(mPoster);
        mName.setText(mMovie.getName());
        mYear.setText(mMovie.getYear());
        mDuration.setText(mMovie.getDuration());
        mSummary.setText(mMovie.getSummary());
        mRating.setText(String.valueOf(mMovie.getScore()) + " / 10");

        return view;
    }
}
