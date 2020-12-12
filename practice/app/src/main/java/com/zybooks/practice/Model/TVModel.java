package com.zybooks.practice.Model;

import android.os.Parcel;
import android.os.Parcelable;
    //Model for tv shows
public class TVModel implements Parcelable {
    private int id;
    private float vote_average;
    private String original_name;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private String[] genre_ids;
    private String first_air_date;

    public TVModel() {
    }

    public TVModel(int id, float vote_average, String original_name, String overview, String poster_path, String backdrop_path, String[] genre_ids, String first_air_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.original_name = original_name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.first_air_date = first_air_date;
    }

    protected TVModel(Parcel in) {
        id = in.readInt();
        vote_average = in.readFloat();
        original_name = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        genre_ids = in.createStringArray();
        first_air_date = in.readString();
    }

    public static final Creator<TVModel> CREATOR = new Creator<TVModel>() {
        @Override
        public TVModel createFromParcel(Parcel in) {
            return new TVModel(in);
        }

        @Override
        public TVModel[] newArray(int size) {
            return new TVModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String[] getGenre_ids() {
        return genre_ids;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(vote_average);
        dest.writeString(original_name);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeStringArray(genre_ids);
        dest.writeString(first_air_date);
    }
}
