package com.zybooks.practice.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Model.TVModel;

import java.util.List;

public class MovieResponse {

    @SerializedName("page")
    @Expose
    private int count;

    @SerializedName("total_results")
    @Expose
    private int total_results;

    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    @SerializedName("results")
    @Expose
    private List<MovieModel> movies;

    public List<MovieModel> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "count=" + count +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", movies=" + movies +
                '}';
    }
}
