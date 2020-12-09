package com.zybooks.practice;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.zybooks.practice.Adapter.MovieAdapter;
import com.zybooks.practice.Adapter.OnMovieListener;
import com.zybooks.practice.Adapter.OnRecommendListener;
import com.zybooks.practice.Adapter.RecommendMovieAdapter;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Model.TVModel;
import com.zybooks.practice.Model.TrailerModel;
import com.zybooks.practice.requests.MovieApi;
import com.zybooks.practice.requests.ServiceGenerator;
import com.zybooks.practice.requests.responses.MovieDetailResponse;
import com.zybooks.practice.requests.responses.MovieResponse;
import com.zybooks.practice.requests.responses.ShowDetailResponse;
import com.zybooks.practice.requests.responses.TrailerResponse;
import com.zybooks.practice.util.Constants;
import com.zybooks.practice.viewmodels.MovieDetailsModel;

import java.text.MessageFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailActivity extends AppCompatActivity implements OnRecommendListener {
    private static final String TAG = "MovieDetailActivity";
    private ImageView mMovieImage;
    private TextView mMovieTitle, mReleaseDate, mMovieDescription;
    private MovieDetailsModel mMovieDetailsViewModel;
    private YouTubePlayerView youTubePlayerView;
    private RecyclerView mRecommendRecyclerView;
    private RecommendMovieAdapter mAdapter;
    private Button mButton;
    final private String MovieData = "movies";
    final private String TvData = "shows";
    private String mURL = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mButton = findViewById(R.id.movie_button);
        mMovieImage = findViewById(R.id.detail_image);
        mMovieTitle = findViewById(R.id.title_view);
        mReleaseDate = findViewById(R.id.detail_release_date);
        mMovieDescription = findViewById(R.id.descriptionView);
        youTubePlayerView = findViewById(R.id.video_view);
        mRecommendRecyclerView = findViewById(R.id.recommendedRecyclerView);
        mMovieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsModel.class);




        initRecyclerView();
        initViewModel();
        subscribeObservers();
    }

    private void initRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new RecommendMovieAdapter(this);
            mRecommendRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            mRecommendRecyclerView.setAdapter(mAdapter);
            mRecommendRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecommendRecyclerView.setNestedScrollingEnabled(true);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initViewModel() {
        if (getIntent().hasExtra(TvData)) {
            TVModel tvDetails = getIntent().getParcelableExtra(TvData);
            mMovieDetailsViewModel.init(tvDetails.getId());
            Log.e(TAG, "initViewModel: " + tvDetails.getId() );
        }
        if (getIntent().hasExtra(MovieData)) {
            MovieModel movieDetails = getIntent().getParcelableExtra(MovieData);
            mMovieDetailsViewModel.init(movieDetails.getId());
            Log.e(TAG, "initViewModel: " + movieDetails.getId() );
        }
    }

    private void subscribeObservers() {

        mMovieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsModel.class);
        //get trailer data
        mMovieDetailsViewModel.getTrailer().observe(this, new Observer<TrailerResponse>() {
            @Override
            public void onChanged(TrailerResponse trailerResponse) {
                //reset player to be visible
                youTubePlayerView.setVisibility(View.VISIBLE);
                List<TrailerModel> list = trailerResponse.getMovies();
                Log.d(TAG, "onChanged1: " + list);
                //if there isn't a movie trailer hide the trailer
                if (list.size() == 0) {
                    youTubePlayerView.setVisibility(View.GONE);
                } else {
                    //get first trailer in the list
                    String videoId = list.get(0).getKey();
                    getLifecycle().addObserver(youTubePlayerView);
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            youTubePlayer.loadVideo(videoId, 0);
                        }
                    });
                }
            }
        });
        //if it's a movie data then update movie recommendation and details
        if (getIntent().hasExtra(MovieData)) {
            MovieModel movieDetails = getIntent().getParcelableExtra(MovieData);
            mMovieDetailsViewModel.init(movieDetails.getId());
            mMovieDetailsViewModel.getMovieRecommendations().observe(this, new Observer<MovieResponse>() {

                @Override
                public void onChanged(MovieResponse movieResponse) {

                    List<MovieModel> list = movieResponse.getMovies();
                    Log.d(TAG, "onChanged2: " + list);
                    mAdapter.setMovies(list);
                    mAdapter.notifyDataSetChanged();

                    mRecommendRecyclerView.setAdapter(mAdapter);
                }
            });
            mMovieDetailsViewModel.getMovieDetails().observe(this, new Observer<MovieDetailResponse>() {
                @Override
                public void onChanged(MovieDetailResponse movieDetailResponse) {
                    mButton.setVisibility(View.GONE);
                    String year = "";
                    year = movieDetailResponse.getRelease_date().substring(0, 4);
                    mMovieTitle.setText(movieDetailResponse.getOriginal_title());
                    mReleaseDate.setText(year);
                    mMovieDescription.setText(MessageFormat.format("     ", movieDetailResponse.getOverview()));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + movieDetailResponse.getPoster_path();
                    Picasso.get().load(posterPath).into(mMovieImage);
                    mURL = movieDetailResponse.getHomepage();
                    if (movieDetailResponse.getHomepage() != null){
                        Log.d(TAG, "onChanged: " + movieDetailResponse.getHomepage());
                        mButton.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
        //if it's a show data then update show recommendation and details
        if (getIntent().hasExtra(TvData)) {
            TVModel showDetails = getIntent().getParcelableExtra(TvData);
            Log.e(TAG, "subscribeObservers: " + showDetails.getId() );
            mMovieDetailsViewModel.init(showDetails.getId());
            mMovieDetailsViewModel.getShowDetails().observe(this, new Observer<ShowDetailResponse>() {
                @Override
                public void onChanged(ShowDetailResponse showDetailResponse) {

                    Log.d(TAG, "onChanged3: sddddddddddddddddddddddddddddddddddddddddddd " + showDetailResponse.getName());
                    String year = "";
                    year = showDetailResponse.getLast_air_date().substring(0, 4);
                    mMovieTitle.setText(showDetailResponse.getName());
                    mReleaseDate.setText(year);
                    mMovieDescription.setText(MessageFormat.format("     ", showDetailResponse.getOverview()));
                    String posterPath = "https://image.tmdb.org/t/p/original/" + showDetailResponse.getPoster_path();
                    Picasso.get().load(posterPath).into(mMovieImage);
                }
            });
            mMovieDetailsViewModel.getShowRecommendation().observe(this, new Observer<MovieResponse>() {

                @Override
                public void onChanged(MovieResponse movieResponse) {

                    List<MovieModel> list = movieResponse.getMovies();

                    Log.d(TAG, "onChanged2: " + list);
                    mAdapter.setMovies(list);
                    mAdapter.notifyDataSetChanged();

                    mRecommendRecyclerView.setAdapter(mAdapter);
                }
            });
        }


    }


    public void onClickOpenWebpageButton(View v) {
        Log.d(TAG, "onClickOpenWebpageButton: " + mURL);
                openWebPage(mURL);
    }

    private void openWebPage(String mURL) {
        Uri webpage = Uri.parse(mURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onRecommendMovieClick(int position) {
        Intent intent = new Intent(this.getApplicationContext(), MovieDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra(MovieData, mAdapter.getSelectedMovie(position));
        this.getApplicationContext().startActivity(intent);

        startActivity(intent);
    }

}
