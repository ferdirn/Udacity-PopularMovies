package com.aldoapps.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aldokelvianto on 3/15/16.
 */
public class FlagPreference {

    public static final String FLAG_PREF_KEY = "flag_pref_key";
    public static final String SORT_BY_KEY = "sort_by_key";

    public static final int SORT_BY_POPULARITY = 0;
    public static final int SORT_BY_HIGHEST_RATED = 1;
    public static final int SORT_BY_FAVORITE = 2;
    public static final int SORT_BY_DEFAULT = SORT_BY_POPULARITY;

    public static int getFlag(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SORT_BY_KEY, SORT_BY_DEFAULT);
    }

    public static void setToFavorite(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SORT_BY_KEY, SORT_BY_FAVORITE);
        editor.apply();
    }

    public static void setToHighestRated(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SORT_BY_KEY, SORT_BY_HIGHEST_RATED);
        editor.apply();
    }

    public static void setToPopularity(Context context){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(FLAG_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SORT_BY_KEY, SORT_BY_POPULARITY);
        editor.apply();
    }
}
