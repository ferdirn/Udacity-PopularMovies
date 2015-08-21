package com.aldoapps.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aldokelvianto on 21/08/15.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.movie_list_item, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(v);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
