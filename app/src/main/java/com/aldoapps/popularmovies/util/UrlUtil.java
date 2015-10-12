package com.aldoapps.popularmovies.util;

import com.aldoapps.popularmovies.MovieConst;

/**
 * Created by user on 11/10/2015.
 */
public class UrlUtil {
    public static String generatePosterUrl(String posterPath){
        // example final image URL
        // http://image.tmdb.org/t/p/w185/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg
        return MovieConst.IMAGE_BASE_URL + MovieConst.POSTER_SIZE_PARAM + posterPath;
    }
}
