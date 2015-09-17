package com.aldoapps.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private ProgressDialog mProgressDialog;
    private Context mContext;

    public static final String TAG = DetailFragment.class.getSimpleName();

    public static DetailFragment newInstance(Movie movie){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Movie.KEY, movie);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mMovie = getArguments().getParcelable(Movie.KEY);
        }
    }

    public void executeFetchMovieDetail(String movieId){
        FetchMovieDetail task = new FetchMovieDetail();
        task.execute(movieId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        Glide.with(this).load(mMovie.getPosterUrl()).into(mPoster);
        mName.setText(mMovie.getName());
        mYear.setText(mMovie.getYear());
        mDuration.setText(mMovie.getDuration());
        mSummary.setText(mMovie.getSummary());
        mRating.setText(String.valueOf(mMovie.getScore()) + " / 10");

        executeFetchMovieDetail(mMovie.getId());

        return view;
    }

    public String generateGetMovieDetailUrl(String movieId){

        Uri.Builder builder = Uri.parse(Movie.MOVIE_DETAIL_BASE_URL + movieId).buildUpon()
                .appendQueryParameter(Movie.API_KEY_PARAM,
                        getActivity().getResources().getString(R.string.API_KEY));

        Uri uri = builder.build();

        return uri.toString();
    }

    @Override
    public void onPause() {
        super.onPause();

        mProgressDialog.dismiss();
    }

    public class FetchMovieDetail extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(getString(R.string.please_wait));

            if(mProgressDialog != null && !mProgressDialog.isShowing())
                mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            setMovieRating(jsonString);

            if(mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();

            try {
                Log.d("asdf", "hasil url " + generateGetMovieDetailUrl(params[0]));
                URL url = new URL(generateGetMovieDetailUrl(params[0]));
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

                if(sb.length() == 0){
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }

                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }
    }

    private void setMovieRating(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            mDuration.setText(jsonObject.getInt(Movie.MOVIE_RUNTIME) + " minutes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
