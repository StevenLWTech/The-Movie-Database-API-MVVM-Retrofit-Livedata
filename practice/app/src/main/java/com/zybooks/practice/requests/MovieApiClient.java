package com.zybooks.practice.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zybooks.practice.requests.responses.MovieDetailResponse;
import com.zybooks.practice.util.AppExecutors;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.requests.responses.MovieSearchResponse;
import com.zybooks.practice.util.Constants;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.zybooks.practice.util.Constants.NETWORK_TIMEOUT;

public class MovieApiClient {


    private MutableLiveData<Boolean> mMovieRequestTimeout = new MutableLiveData<>();
    private static MovieApiClient instance;
    private static final String TAG = "MovieApiClient";
    private MutableLiveData<List<MovieModel>> mMovies;

    private RetrieveMoviesRunnable mRetrieveMoviesRunnable;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    public MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public void searchMoviesApi(String query, int pageNumber) {
        //clear old object data
        if (mRetrieveMoviesRunnable != null) {
            mRetrieveMoviesRunnable = null;
        }
        //call runnable passing query and pagenumber to executor
        mRetrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveMoviesRunnable);
        //Execute runnable task
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    /**
     * Runs Search Query passing user query and pagenumber
     */
    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getSearchMovies(query, pageNumber).execute();
                // if user cancels then return
                if (cancelRequest) {
                    cancelRequest();
                    return;
                }
                // if response was successful
                if (response.code() == 200) {
                    //retrieve list of movies
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    //if it's first page send list to live data
                    if (pageNumber == 1) {
                        mMovies.postValue(list);
                        //else append the results to the currentlist if pagenumber isn't 1
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                    //request was unsuccessful return null list
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mMovies.postValue(null);
                }
                //catches ioexception
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<MovieSearchResponse> getSearchMovies(String query, int pageNumber) {
            return ServiceGenerator.getMovieApi().searchMovie(
                    Constants.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request.");
            cancelRequest = true;
        }
    }


}