package com.aldoapps.popularmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldoapps.popularmovies.data.FlagPreference;
import com.aldoapps.popularmovies.model.discover.Movie;
import com.aldoapps.popularmovies.util.MovieConst;
import com.aldoapps.popularmovies.util.UrlUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aldokelvianto on 21/08/15.
 */
public class MoviePosterAdapter extends BaseAdapter{

    private Context mContext;
    private List<Movie> mMovies;
    private LayoutInflater mInflater;
    private boolean isFavorite = false;

    public MoviePosterAdapter(Context context, List<Movie> movies){
        mContext = context;
        mMovies = movies;

        mInflater = LayoutInflater.from(context);
    }

    public void setIsFavorite(boolean flag){
        isFavorite = flag;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieViewHolder cell;
        Movie movie = getItem(position);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.movie_poster_list_item, parent, false);
            cell = new MovieViewHolder(convertView);
            convertView.setTag(cell);
        } else {
            cell = (MovieViewHolder) convertView.getTag();
        }

        if(isFavorite) {

            String posterPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + MovieConst.DIR_NAME + "/" + String.valueOf(movie.getId()) + ".png";
            cell.moviePoster.setImageDrawable(Drawable.createFromPath(posterPath));
        }else{
            Picasso.with(mContext)
                    .load(UrlUtil.generatePosterUrl(movie.getPosterPath()))
                    .into(cell.moviePoster);
        }

        cell.movieName.setText(movie.getTitle());

        return convertView;
    }

    class MovieViewHolder{
        @Bind(R.id.movie_poster) ImageView moviePoster;
        @Bind(R.id.movie_name) TextView movieName;

        public MovieViewHolder(View view){
            ButterKnife.bind(this, view);
        }

    }
}
