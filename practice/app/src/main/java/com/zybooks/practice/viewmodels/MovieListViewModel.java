package com.zybooks.practice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.repositories.MovieRepository;
import com.zybooks.practice.requests.responses.MovieResponse;
import com.zybooks.practice.requests.responses.TvResponse;
import com.zybooks.practice.util.Constants;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MovieRepository mMovieRepository;
    private MutableLiveData<MovieResponse> popularMovieLiveData, trendingMovieLiveData, actionMovieLiveData, mysteryMovieLiveData, familyMovieLiveData;
    private MutableLiveData<TvResponse> popularShowsLiveData, topRatedShowsLiveData, sciFiShowsLiveData, warShowsLiveData;


    //Constructor get an instance of MovieRepository
    public MovieListViewModel() {
        mMovieRepository = MovieRepository.getInstance();
    }

    public void init() {
        if (popularMovieLiveData != null) {
            return;
        }
        mMovieRepository = MovieRepository.getInstance();
        //Api calls to populate data
        popularMovieLiveData = mMovieRepository.getPopular(Constants.API_KEY, "videos");
        trendingMovieLiveData = mMovieRepository.getTrending("movie", "day", Constants.API_KEY, "videos");
        actionMovieLiveData = mMovieRepository.getAction(Constants.API_KEY, Constants.SORT_BY, Constants.ACTION_MOVIES);
        mysteryMovieLiveData = mMovieRepository.getMystery(Constants.API_KEY,Constants.SORT_BY, Constants.MYSTERY_MOVIES);
        familyMovieLiveData = mMovieRepository.getFamily(Constants.API_KEY, Constants.SORT_BY, Constants.FAMILY_MOVIES);

        popularShowsLiveData = mMovieRepository.getPopularShow(Constants.API_KEY, "videos");
        topRatedShowsLiveData = mMovieRepository.getTopRatedShows(Constants.API_KEY);
        sciFiShowsLiveData = mMovieRepository.getSciFiShows(Constants.API_KEY, Constants.SORT_BY, Constants.SCI_FI_SHOWS);
        warShowsLiveData = mMovieRepository.getWarShows(Constants.API_KEY, Constants.SORT_BY, Constants.WAR_SHOWS);
    }
    // pass data from repository to MovieListViewModel
    public LiveData<List<MovieModel>> getMovies() {
        return mMovieRepository.getMovies();
    }

    // method passes data to Repository
    public void searchMoviesApi(String query, int pageNumber) {
        mMovieRepository.searchMoviesApi(query, pageNumber);
    }
    //region livedata
    public LiveData<MovieResponse> getPopularMovies() {
        return popularMovieLiveData;
    }
    public LiveData<MovieResponse> getTrendingMovies() {
        return trendingMovieLiveData;
    }
    public LiveData<MovieResponse> getMysteryMovies() {
        return mysteryMovieLiveData;
    }
    public LiveData<MovieResponse> getActionMovies() {
        return actionMovieLiveData;
    }
    public LiveData<MovieResponse> getFamilyMovies() {
        return familyMovieLiveData;
    }
    public LiveData<TvResponse> getPopularShows() {
        return popularShowsLiveData;
    }
    public LiveData<TvResponse> getTopRatedShows() {
        return topRatedShowsLiveData;
    }
    public LiveData<TvResponse> getSciFiShows() {
        return sciFiShowsLiveData;
    }
    public LiveData<TvResponse> getWarShows() {
        return warShowsLiveData;
    }

    //endregion
}
