package com.zc.zplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SongList implements Parcelable {
    private ArrayList<Song> songs;
    private String name;

    public SongList() {
        songs = new ArrayList<>();
    }

    public SongList(Parcel in) {
        songs = in.createTypedArrayList(Song.CREATOR);
        name = in.readString();
    }

    public static final Creator<SongList> CREATOR = new Creator<SongList>() {
        @Override
        public SongList createFromParcel(Parcel in) {
            return new SongList(in);
        }

        @Override
        public SongList[] newArray(int size) {
            return new SongList[size];
        }
    };

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(songs);
        dest.writeString(name);
    }
}
