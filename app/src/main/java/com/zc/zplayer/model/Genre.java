package com.zc.zplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {
    private int id;
    private int count;
    private String genreTitle;

    public Genre(int id, String genreTitle, int count) {
        this.id = id;
        this.count = count;
        this.genreTitle = genreTitle;
    }

    private Genre(Parcel in) {
        id = in.readInt();
        count = in.readInt();
        genreTitle = in.readString();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(count);
        dest.writeString(genreTitle);
    }
}
