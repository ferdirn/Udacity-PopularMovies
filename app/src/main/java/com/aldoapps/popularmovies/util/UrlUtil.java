package com.aldoapps.popularmovies.util;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by user on 11/10/2015.
 */
public class UrlUtil {
    public static String generatePosterUrl(String posterPath){
        // example final image URL
        // http://image.tmdb.org/t/p/w185/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg
        return MovieConst.IMAGE_BASE_URL + MovieConst.POSTER_SIZE_PARAM + posterPath;
    }

    public static Intent watchYoutubeVideo(String id){
        Intent intent = null;
        try{
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        }catch (ActivityNotFoundException ex){
            intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
        }finally {
            return intent;
        }
    }
}
