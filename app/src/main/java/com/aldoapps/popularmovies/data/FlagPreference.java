package com.aldoapps.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.aldoapps.popularmovies.util.MovieConst;

/**
 * Created by aldokelvianto on 3/15/16.
 */
public class FlagPreference {

    // key for this shared prefs
    public static final String FLAG_PREF_KEY = "flag_pref_key";

    // key for getting flags
    public static final String SORT_BY_KEY = "sort_by_key";

    public static final String SORT_BY_POPULARITY = MovieConst.SORT_BY_POPULARITY_DESC;
    public static final String SORT_BY_HIGHEST_RATED = MovieConst.SORT_BY_HIGHEST_RATED_DESC;
    public static final String SORT_BY_FAVORITE = MovieConst.SORT_BY_FAVORITE_DESC;
    public static final String SORT_BY_DEFAULT = SORT_BY_POPULARITY;

    public static String getFlag(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SORT_BY_KEY, SORT_BY_DEFAULT);
    }

    public static void setToFavorite(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_BY_KEY, SORT_BY_FAVORITE);
        editor.commit();
    }

    public static void setToHighestRated(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_BY_KEY, SORT_BY_HIGHEST_RATED);
        editor.commit();
    }

    public static void setToPopularity(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_BY_KEY, SORT_BY_POPULARITY);
        editor.commit();
    }
}
