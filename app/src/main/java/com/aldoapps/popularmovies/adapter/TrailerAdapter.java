package com.aldoapps.popularmovies.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aldoapps.popularmovies.model.trailer.Trailer;

import java.util.List;

/**
 * Created by user on 19/10/2015.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer>{
    private Context mContext;
    private List<Trailer> mTrailer;

    public TrailerAdapter(Context context, int resource, List<Trailer> trailers) {
        super(context, resource);

        mTrailer = trailers;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }
}
