package com.zc.zplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Album implements Parcelable {

    private String id;
    private String albumTitle;
    private String albumArtist;
    private String albumID;
    private final String ALBUM_PATH = "content://media/external/audio/albumart";

    public Album(String id, String albumTitle, String albumArtist, String albumID) {
        this.id = id;
        this.albumTitle = albumTitle;
        this.albumArtist = albumArtist;
        this.albumID = albumID;
    }

    protected Album(Parcel in) {
        id = in.readString();
        albumTitle = in.readString();
        albumArtist = in.readString();
        albumID = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getAlbumArt() {
        return albumID;
    }

    public void setAlbumArt(String albumID) {
        this.albumID = albumID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(albumTitle);
        dest.writeString(albumArtist);
        dest.writeString(albumID);
        dest.writeString(ALBUM_PATH);
    }
}
