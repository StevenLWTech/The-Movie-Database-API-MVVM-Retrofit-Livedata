package com.zybooks.practice.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zybooks.practice.Model.MovieModel;
import com.zybooks.practice.Model.TVModel;

import java.util.List;

public class TvResponse {

    @SerializedName("page")
    @Expose
    private int count;

    @SerializedName("results")
    @Expose
    private List<TVModel> shows;

    public List<TVModel> getTVShows() {
        return shows;
    }

    @Override
    public String toString() {
        return "TvResponse{" +
                "count=" + count +
                ", shows=" + shows +
                '}';
    }
}
