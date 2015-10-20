package com.aldoapps.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aldoapps.popularmovies.R;
import com.aldoapps.popularmovies.model.review.Review;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Created by user on 20/10/2015.
 */
public class CommentAdapter extends BaseAdapter {

    private List<Review> mComments;
    private Context mContext;
    private LayoutInflater mInflater;

    public CommentAdapter(Context context, List<Review> comments) {
        mComments = comments;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Review getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.comment_item, parent, false);

        TextView authorTextView = (TextView) convertView.findViewById(R.id.author_text);
        authorTextView.setText(getItem(position).getAuthor());

        TextView reviewTextView = (TextView) convertView.findViewById(R.id.review_text);
        reviewTextView.setText(getItem(position).getContent());

        return convertView;
    }
}
