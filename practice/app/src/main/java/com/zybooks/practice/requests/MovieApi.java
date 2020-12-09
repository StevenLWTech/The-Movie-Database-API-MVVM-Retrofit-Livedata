package com.zybooks.practice.requests;

import com.zybooks.practice.requests.responses.MovieDetailResponse;
import com.zybooks.practice.requests.responses.MovieSearchResponse;
import com.zybooks.practice.requests.responses.ShowDetailResponse;
import com.zybooks.practice.requests.responses.TrailerResponse;
import com.zybooks.practice.requests.responses.TvResponse;
import com.zybooks.practice.requests.responses.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // Search Movies
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );
    // GET movie details
    @GET("/3/movie/{movie_id}")
    Call<MovieDetailResponse> getMovieDetails(
            @Path("movie_id") int id,
            @Query("api_key") String key
    );
    // GET movie details
    @GET("/3/tv/{tv_id}")
    Call<ShowDetailResponse> getTVDetails(
            @Path("tv_id") int id,
            @Query("api_key") String key
    );
    // GET movie details
    @GET("/3/movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(
            @Path("movie_id") int id,
            @Query("api_key") String key
    );

    // Get Trending Movies
    @GET("/3/trending/{media_type}/{time_window}")
    Call<MovieResponse> getTrendingMovies(
            @Path("media_type") String media_type,
            @Path("time_window") String time_window,
            @Query("api_key") String key,
            @Query("append_to_response") String trailer
    );
    // Get movies with genre filter
    @GET("/3/discover/movie")
    Call<MovieResponse> discoverMovies(
            @Query("api_key") String key,
            @Query("sort_by") String sort_by,
            @Query("with_genres") String with_genres
    );
    // Get Popular Movies
    @GET("/3/movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("append_to_response") String trailer
    );
    // Get Popular TV Shows
    @GET("/3/tv/popular")
    Call<TvResponse> getPopularTV(
            @Query("api_key") String key,
            @Query("append_to_response") String trailer
    );
    // Get Top Rated TV Shows
    @GET("/3/tv/top_rated")
    Call<TvResponse> getTopRatedTV(
            @Query("api_key") String key
    );
    // Get TV Shows by genre
    @GET("/3/discover/tv")
    Call<TvResponse> discoverTv(
            @Query("api_key") String key,
            @Query("sort_by") String sort_by,
            @Query("with_genres") String with_genres
    );
    // Get movie recommendations
    @GET("3/movie/{movie_id}/recommendations")
    Call<MovieResponse> getMovieRecommendations(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );
    // Get show recommendations
    @GET("3/tv/{tv_id}/recommendations")
    Call<MovieResponse> getShowRecommendations(
            @Path("tv_id") int movie_id,
            @Query("api_key") String key
    );
}
