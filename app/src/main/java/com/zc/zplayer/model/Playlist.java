package com.zc.zplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Playlist implements Parcelable {
    private long id;
    private int count;
    private String data;
    private String playlistName;

    public Playlist(long id, int count, String data, String playlistName) {
        this.id = id;
        this.count = count;
        this.data = data;
        this.playlistName = playlistName;
    }

    protected Playlist(Parcel in) {
        id = in.readLong();
        count = in.readInt();
        data = in.readString();
        playlistName = in.readString();
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(count);
        dest.writeString(data);
        dest.writeString(playlistName);
    }
}
