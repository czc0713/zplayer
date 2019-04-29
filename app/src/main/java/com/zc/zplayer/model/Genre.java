package com.zc.zplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {
    private long id;
    private int count;
    private String genreTitle;

    public Genre(long id, int count, String genreTitle) {
        this.id = id;
        this.count = count;
        this.genreTitle = genreTitle;
    }

    protected Genre(Parcel in) {
        id = in.readLong();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
