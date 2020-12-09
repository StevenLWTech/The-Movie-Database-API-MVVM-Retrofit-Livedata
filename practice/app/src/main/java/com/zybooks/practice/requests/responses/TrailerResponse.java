package com.zybooks.practice.requests.responses;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zybooks.practice.Model.TrailerModel;

import java.util.List;

public class TrailerResponse {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("results")
    @Expose
    private List<TrailerModel> trailers;

    public List<TrailerModel> getMovies() {
        return trailers;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
