package com.zybooks.practice.requests.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class MovieDetailResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vote_average")
    @Expose
    private float vote_average;
    @SerializedName("original_title")
    @Expose
    private String original_title;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("poster_path")
    @Expose
    private String poster_path;
    @SerializedName("backdrop_path")
    @Expose
    private String backdrop_path;
    @SerializedName("release_date")
    @Expose
    private String release_date;
    @SerializedName("genre_ids")
    @Expose
    private String[] genres;

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public MovieDetailResponse(int id, float vote_average, String original_title, String overview, String homepage, String poster_path, String backdrop_path, String release_date, String[] genres) {
        this.id = id;
        this.vote_average = vote_average;
        this.original_title = original_title;
        this.overview = overview;
        this.homepage = homepage;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "MovieDetailResponse{" +
                "id=" + id +
                ", vote_average=" + vote_average +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", homepage='" + homepage + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", genres=" + Arrays.toString(genres) +
                '}';
    }
}