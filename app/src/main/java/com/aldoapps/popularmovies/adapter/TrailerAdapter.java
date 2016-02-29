package com.aldoapps.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldoapps.popularmovies.R;
import com.aldoapps.popularmovies.model.trailer.Trailer;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by user on 19/10/2015.
 */
public class TrailerAdapter extends BaseAdapter{

    private Context mContext;
    private List<Trailer> mTrailer;
    private LayoutInflater mInflater;

    public TrailerAdapter(Context context, List<Trailer> trailers) {
        mTrailer = trailers;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTrailer.size();
    }

    @Override
    public Trailer getItem(int position) {
        return mTrailer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.trailer_item, parent, false);

        String thumbnailUrl = UrlUtil.getVideoThumbnail(mTrailer.get(0).getKey());

        ImageView trailerThumbnail = (ImageView) convertView.findViewById(R.id.trailer_image);
        Glide.with(mContext).load(thumbnailUrl).into(trailerThumbnail);

        TextView trailerNumber = (TextView) convertView.findViewById(R.id.trailer_name);
        trailerNumber.setText(mContext.getString(R.string.trailer) + " " + (position + 1));

        return convertView;
    }


}
