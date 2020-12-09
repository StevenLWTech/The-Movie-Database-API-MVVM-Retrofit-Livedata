package com.zybooks.practice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zybooks.practice.repositories.MovieRepository;
import com.zybooks.practice.requests.responses.MovieDetailResponse;
import com.zybooks.practice.requests.responses.MovieResponse;
import com.zybooks.practice.requests.responses.ShowDetailResponse;
import com.zybooks.practice.requests.responses.TrailerResponse;
import com.zybooks.practice.util.Constants;


public class MovieDetailsModel extends ViewModel {

    private MovieRepository mMovieRepository;
    private String mMovieId;
    private boolean mDidRetrieveMovie;
    private MutableLiveData<TrailerResponse> movieTrailerData;
    private MutableLiveData<MovieResponse> movieRecommendedData;
    private MutableLiveData<MovieResponse> showRecommendedData;
    private MutableLiveData<MovieDetailResponse> movieDetailsData;
    private MutableLiveData<ShowDetailResponse> tvDetailsData;


    public MovieDetailsModel() {
        mMovieRepository = MovieRepository.getInstance();
        mDidRetrieveMovie = false;
    }
    public void init(int id) {
        movieTrailerData = mMovieRepository.getMovieTrailer(id,Constants.API_KEY);
        movieRecommendedData = mMovieRepository.getRecommendedMovies(id, Constants.API_KEY);
        showRecommendedData = mMovieRepository.getRecommendedShow(id, Constants.API_KEY);
        movieDetailsData = mMovieRepository.getMovieDetails(id, Constants.API_KEY);
        tvDetailsData = mMovieRepository.getTVDetails(550, Constants.API_KEY);
//        tvDetailsData = mMovieRepository.getTVDetails(id, Constants.API_KEY);
    }

    public LiveData<TrailerResponse> getTrailer() {
        return movieTrailerData;
    }
    public LiveData<MovieResponse> getMovieRecommendations() {
        return movieRecommendedData;
    }

    public LiveData<MovieDetailResponse> getMovieDetails() {
        return movieDetailsData;
    }


    public LiveData<MovieResponse> getShowRecommendation() {
        return showRecommendedData;
    }

    public LiveData<ShowDetailResponse> getShowDetails() {
        return tvDetailsData;
    }
}