package com.aldoapps.popularmovies;

import com.aldoapps.popularmovies.model.discover.DiscoverResponse;
import com.aldoapps.popularmovies.model.movie_detail.MovieDetail;
import com.aldoapps.popularmovies.model.review.ReviewResponse;
import com.aldoapps.popularmovies.model.trailer.TrailerResponse;
import com.aldoapps.popularmovies.util.MovieConst;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit Interface
 */
public interface TmdbApi {

    @GET("3/discover/movie")
    Call<DiscoverResponse> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                            @Query(MovieConst.API_KEY_PARAM) String apiKey
    );

    @GET("3/discover/movie")
    Call<DiscoverResponse> discoverMoviesPage(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                            @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                              @Query(MovieConst.PAGE_PARAM) int pageNumber
    );

    // sort by highest rated, require another two parameter
    // average votes and vote count (to make sure its not some
    // random movie with only a few people rate it 10) minimum of 1000 people
    @GET("3/discover/movie")
    Call<DiscoverResponse> discoverMovies(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                                 @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                                 @Query(MovieConst.VOTE_AVERAGE_PARAM) String voteAvg,
                                                 @Query(MovieConst.VOTE_COUNT_PARAM) String voteCount
    );

    @GET("3/discover/movie")
    Call<DiscoverResponse> discoverMoviesPage(@Query(MovieConst.SORT_BY_PARAM) String sortBy,
                                                 @Query(MovieConst.API_KEY_PARAM) String apiKey,
                                                 @Query(MovieConst.VOTE_AVERAGE_PARAM) String voteAvg,
                                                 @Query(MovieConst.VOTE_COUNT_PARAM) String voteCount,
                                              @Query(MovieConst.PAGE_PARAM) int pageNumber
    );

    @GET("3/movie/{id}")
    Call<MovieDetail> getMovieDetail(@Path("id") int movieId,
                               @Query(MovieConst.API_KEY_PARAM) String apiKey
                               );

    // movie review id sample: 49026 - The Dark Knight
    @GET("3/movie/{id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("id") int movieId,
                                @Query(MovieConst.API_KEY_PARAM) String apiKey
                                );

    // movie trailer id sample: 550 - Fight Club
    @GET("3/movie/{id}/videos")
    Call<TrailerResponse> getMovieTrailers(@Path("id") int movieId,
                                 @Query(MovieConst.API_KEY_PARAM) String apiKey
                                 );
}
