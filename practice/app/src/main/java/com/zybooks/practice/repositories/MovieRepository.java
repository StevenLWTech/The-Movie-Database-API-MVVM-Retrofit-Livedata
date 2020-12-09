package com.zybooks.practice.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.requests.MovieApi;
import com.zybooks.practice.requests.MovieApiClient;
import com.zybooks.practice.requests.ServiceGenerator;
import com.zybooks.practice.requests.responses.MovieDetailResponse;
import com.zybooks.practice.requests.responses.MovieResponse;
import com.zybooks.practice.requests.responses.ShowDetailResponse;
import com.zybooks.practice.requests.responses.TrailerResponse;
import com.zybooks.practice.requests.responses.TvResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static MovieRepository instance;
    private MovieApiClient mMovieApiClient;
    private String mQuery;
    private int mPageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<MovieModel>> mMovies = new MediatorLiveData<>();
    MovieApi moviesApi = ServiceGenerator.getMovieApi();
    MutableLiveData<MovieResponse> popularData = new MutableLiveData<>();
    MutableLiveData<MovieResponse> trendingData = new MutableLiveData<>();
    MutableLiveData<MovieResponse> actionData = new MutableLiveData<>();
    MutableLiveData<MovieResponse> mysteryData = new MutableLiveData<>();
    MutableLiveData<MovieResponse> familyData = new MutableLiveData<>();
    MutableLiveData<MovieResponse> recommendedMovieData = new MutableLiveData<>();
    MutableLiveData<MovieResponse> recommendedTVData = new MutableLiveData<>();
    MutableLiveData<TvResponse> popularTvData = new MutableLiveData<>();
    MutableLiveData<TvResponse> topRatedTvData = new MutableLiveData<>();
    MutableLiveData<TvResponse> sciFiTvData = new MutableLiveData<>();
    MutableLiveData<TvResponse> warTvData = new MutableLiveData<>();
    MutableLiveData<MovieDetailResponse> movieDetailData = new MutableLiveData<>();
    MutableLiveData<ShowDetailResponse> tvDetailData = new MutableLiveData<>();
    MutableLiveData<TrailerResponse> trailerData = new MutableLiveData<>();


    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        mMovieApiClient = MovieApiClient.getInstance();
        initMediators();

    }

    private void initMediators() {
        LiveData<List<MovieModel>> moviesListApiSource = mMovieApiClient.getMovies();
        mMovies.addSource(moviesListApiSource, movies -> {
            if (movies != null) {
                mMovies.setValue(movies);
            } else {
                // search database cache if null
                doneQuery(null);
            }
        });
    }
    private void doneQuery(List<MovieModel> list) {
        if (list != null) {
            if (list.size() % 20 != 0) {
                mIsQueryExhausted.setValue(true);
            }
        } else mIsQueryExhausted.setValue(true);
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }


    public void searchMoviesApi(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mIsQueryExhausted.setValue(false);
        mMovieApiClient.searchMoviesApi(query, pageNumber);
    }
    //region Responses
    //region MovieResponses
    public MutableLiveData<MovieResponse> getPopular(String key, String append) {

        moviesApi.getPopularMovies(key, append).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call,
                                   Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    popularData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                popularData.setValue(null);
            }
        });
        return popularData;
    }

    public MutableLiveData<MovieResponse> getTrending(String media_type, String time_window, String apiKey, String append_trailer) {
        moviesApi.getTrendingMovies(media_type, time_window, apiKey, append_trailer).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call,
                                   Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    trendingData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                trendingData.setValue(null);
            }
        });
        return trendingData;
    }

    public MutableLiveData<MovieResponse> getAction(String apiKey, String sort, String category) {
        moviesApi.discoverMovies(apiKey, sort, category).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    actionData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                actionData.setValue(null);
            }
        });
        return actionData;
    }

    public MutableLiveData<MovieResponse> getMystery(String apiKey, String sortBy, String mysteryMovies) {
        moviesApi.discoverMovies(apiKey, sortBy, mysteryMovies).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    mysteryData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mysteryData.setValue(null);
            }
        });
        return mysteryData;
    }

    public MutableLiveData<MovieResponse> getFamily(String apiKey, String sortBy, String mysteryMovies) {
        moviesApi.discoverMovies(apiKey, sortBy, mysteryMovies).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    familyData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                familyData.setValue(null);
            }
        });
        return familyData;
    }
    //endregion
    //region TvResponses
    public MutableLiveData<TvResponse> getPopularShow(String key, String append) {
        moviesApi.getPopularTV(key, append).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call,
                                   Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    popularTvData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                popularTvData.setValue(null);
            }
        });
        return popularTvData;
    }

    public MutableLiveData<TvResponse> getTopRatedShows(String key) {
        moviesApi.getTopRatedTV(key).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call,
                                   Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    topRatedTvData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                topRatedTvData.setValue(null);
            }
        });
        return topRatedTvData;
    }

    public MutableLiveData<TvResponse> getSciFiShows(String key, String sortBy, String sciFi) {
        moviesApi.discoverTv(key, sortBy, sciFi).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call,
                                   Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    sciFiTvData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                sciFiTvData.setValue(null);
            }
        });
        return sciFiTvData;
    }

    public MutableLiveData<TvResponse> getWarShows(String key, String sortBy, String war) {
        moviesApi.discoverTv(key, sortBy, war).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(Call<TvResponse> call,
                                   Response<TvResponse> response) {
                if (response.isSuccessful()) {
                    warTvData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvResponse> call, Throwable t) {
                warTvData.setValue(null);
            }
        });
        return warTvData;
    }
    //endregion

    public MutableLiveData<TrailerResponse> getMovieTrailer(int id, String key) {
        moviesApi.getMovieTrailer(id, key).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    trailerData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });
        return trailerData;
    }

    public MutableLiveData<MovieResponse> getRecommendedMovies(int id, String key) {
        moviesApi.getMovieRecommendations(id, key).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    recommendedMovieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return recommendedMovieData;
    }

    public MutableLiveData<MovieDetailResponse> getMovieDetails(int id, String apiKey) {
        moviesApi.getMovieDetails(id, apiKey).enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                if (response.isSuccessful()) {
                    movieDetailData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {

            }
        });
            return movieDetailData;
    }
    public MutableLiveData<ShowDetailResponse> getTVDetails(int id, String apiKey) {
        moviesApi.getTVDetails(id, apiKey).enqueue(new Callback<ShowDetailResponse>() {
            @Override
            public void onResponse(Call<ShowDetailResponse> call, Response<ShowDetailResponse> response) {
                if (response.isSuccessful()) {
                    tvDetailData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ShowDetailResponse> call, Throwable t) {

            }
        });
        return tvDetailData;
    }

    public MutableLiveData<MovieResponse> getRecommendedShow(int id, String apiKey) {
        moviesApi.getShowRecommendations(id, apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    recommendedTVData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return recommendedTVData;
    }
    //endregion
}
