package com.zybooks.practice.Model;

import android.os.Parcel;
import android.os.Parcelable;
    //Model for recommended movies and shows
public class RecommendModel implements Parcelable {
    private int id;
    private String original_title;
    private int vote_average;
    private String poster_path;
    private String backdrop_path;

    protected RecommendModel(Parcel in) {
        id = in.readInt();
        original_title = in.readString();
        vote_average = in.readInt();
        poster_path = in.readString();
        backdrop_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(original_title);
        dest.writeInt(vote_average);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecommendModel> CREATOR = new Creator<RecommendModel>() {
        @Override
        public RecommendModel createFromParcel(Parcel in) {
            return new RecommendModel(in);
        }

        @Override
        public RecommendModel[] newArray(int size) {
            return new RecommendModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public RecommendModel(int id, String original_title, int vote_average, String poster_path, String backdrop_path) {
        this.id = id;
        this.original_title = original_title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }
}
