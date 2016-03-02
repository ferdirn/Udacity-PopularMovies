package com.aldoapps.popularmovies.util;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieConst{

    public static final String KEY = "movie";

    public static final String BASE_URL = "http://api.themoviedb.org/";

    public static final String MOVIE_DETAIL_BASE_URL = BASE_URL + "movie/";
    public static final String DISCOVER_BASE_URL = BASE_URL + "discover/movie";

    public static final String SORT_BY_PARAM = "sort_by";
    public static final String API_KEY_PARAM = "api_key";
    public static final String VOTE_AVERAGE_PARAM = "vote_average.gte";
    public static final String VOTE_COUNT_PARAM = "vote_count.gte";

    public static final String SORT_BY_POPULARITY_DESC = "popularity.desc";
    public static final String SORT_BY_HIGHEST_RATED_DESC = "vote_average.desc";
    public static final String VOTE_AVERAGE_VALUE = "8";
    public static final String VOTE_COUNT_VALUE = "1000";

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String POSTER_SIZE_PARAM = "/w185";
    public static final String BACKDROP_SIZE_PARAM = "/w500";

    public static final int DEFAULT_VALUE = 0;
}
