package com.aldoapps.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by aldokelvianto on 21/08/15.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {
    ImageView movieImage;

    public MovieViewHolder(View itemView) {
        super(itemView);

        movieImage = (ImageView) itemView.findViewById(R.id.movie_poster);
    }
}
