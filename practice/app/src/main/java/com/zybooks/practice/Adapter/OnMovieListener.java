package com.zybooks.practice.Adapter;

import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Model.TVModel;

public interface OnMovieListener {
    //When a Movie View is clicked
    void onMovieClick(MovieModel movieModel);
    // When a Tv View is clicked
    void onTvClick(TVModel tvModel);

}
