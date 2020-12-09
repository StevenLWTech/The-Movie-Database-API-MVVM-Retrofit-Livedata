package com.zybooks.practice.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zybooks.practice.requests.MovieApi;
import com.zybooks.practice.requests.ServiceGenerator;

public class MovieModel implements Parcelable {

    private int id;
    private int runtime;
    private String release_date;
    private float vote_average;
    private String original_title;
    private String overview;
    private String homepage;
    private String poster_path;
    private String backdrop_path;
    private String[] genre_ids;
    private String key;

    protected MovieModel(Parcel in) {
        id = in.readInt();
        runtime = in.readInt();
        release_date = in.readString();
        vote_average = in.readFloat();
        original_title = in.readString();
        overview = in.readString();
        homepage = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        genre_ids = in.createStringArray();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(runtime);
        dest.writeString(release_date);
        dest.writeFloat(vote_average);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(homepage);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeStringArray(genre_ids);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
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

    public String[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(String[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MovieModel() {
    }

    public MovieModel(int id, int runtime, String release_date, float vote_average, String original_title, String overview, String homepage, String poster_path, String backdrop_path, String[] genre_ids, String key) {
        this.id = id;
        this.runtime = runtime;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.original_title = original_title;
        this.overview = overview;
        this.homepage = homepage;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.key = key;
    }
}
