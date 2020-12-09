package com.zybooks.practice.Adapter;

import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Model.TVModel;

public interface OnMovieListener {


    void onMovieClick(MovieModel movieModel);
    void onTvClick(TVModel tvModel);

}
