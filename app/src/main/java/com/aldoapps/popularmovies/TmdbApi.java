package com.aldoapps.popularmovies;

import com.aldoapps.popularmovies.model.DiscoverResponse;
import com.aldoapps.popularmovies.model.Movie;
import com.aldoapps.popularmovies.model.MovieDetail;
import com.aldoapps.popularmovies.util.MovieConst;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Retrofit Interface
 */
public interface TmdbApi {

    // sort by highest rated, require another two parameter
    // average votes and vote count (to make sure its not some
    // random movie with only a few people rate it 10) minimum of 1000 people
    @GET("3/discover/movie")
    Call<DiscoverResponse> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                            @Query(MovieConst.API_KEY_PARAM) String apiKey
    );

    @GET("3/discover/movie")
    Call<DiscoverResponse> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                                 @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                                 @Query(MovieConst.VOTE_AVERAGE_PARAM) String voteAvg,
                                                 @Query(MovieConst.VOTE_COUNT_PARAM) String voteCount
    );

    @GET("3/movie/{id}")
    Call<MovieDetail> getMovieDetail(@Path("id") int movieId,
                               @Query(MovieConst.API_KEY_PARAM) String apiKey
                               );
}
