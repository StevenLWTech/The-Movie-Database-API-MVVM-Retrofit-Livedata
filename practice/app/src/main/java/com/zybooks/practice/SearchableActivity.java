package com.zybooks.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.requests.MovieApi;
import com.zybooks.practice.requests.ServiceGenerator;
import com.zybooks.practice.requests.responses.MovieSearchResponse;
import com.zybooks.practice.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        MovieApi moviesApi = ServiceGenerator.getMovieApi();
        Call<MovieSearchResponse> responseCall = moviesApi
                .searchMovie(
                        Constants.API_KEY,
                        query,
                        "1"
                );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(response.body().getMovies());
//                    setListAdapter();
                }

            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });


    }
}
