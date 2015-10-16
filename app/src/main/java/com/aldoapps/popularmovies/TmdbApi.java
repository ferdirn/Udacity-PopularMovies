package com.aldoapps.popularmovies;

import com.aldoapps.popularmovies.model.DiscoverResponse;
import com.aldoapps.popularmovies.model.Responsez;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Retrofit Interface
 */
public interface TmdbApi {

    // sort by highest rated, require another two parameter
    // average votes and vote count (to make sure its not some
    // random movie with only a few people rate it 10) minimum of 1000 people
    @GET("3/discover/movie")
    Call<Responsez> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                            @Query(MovieConst.API_KEY_PARAM) String apiKey
    );

    @GET("3/discover/movie")
    Call<Responsez> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                                 @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                                 @Query(MovieConst.VOTE_AVERAGE_PARAM) String voteAvg,
                                                 @Query(MovieConst.VOTE_COUNT_PARAM) String voteCount
    );
}
