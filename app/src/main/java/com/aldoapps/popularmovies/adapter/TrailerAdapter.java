package com.aldoapps.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldoapps.popularmovies.R;
import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * created by aldokelvianto on 19/10/2015.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    List<Trailer> mTrailers;

    public TrailerAdapter(List<Trailer> trailers){
        mTrailers = trailers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String movieKey = mTrailers.get(position).getKey();
        String thumbnailUrl = UrlUtil.getVideoThumbnail(movieKey);

        Picasso.with(holder.mImageView.getContext())
                .load(thumbnailUrl)
                .into(holder.mImageView);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mImageView.getContext().startActivity(UrlUtil.watchYoutubeVideo(movieKey));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.trailer_image);
        }
    }

}
