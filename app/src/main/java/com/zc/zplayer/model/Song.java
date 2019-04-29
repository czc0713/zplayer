package com.zc.zplayer.model;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Song implements Parcelable {

    private long id;
    private String data;
    private String songTitle;
    private String songAlbum;
    private String songArtist;
    private long songAlbumID;
    private int duration;
    private String track;
    private final String ALBUM_PATH = "content://media/external/audio/albumart";

    public Song(long id, String data, String songTitle, String songAlbum, String songArtist, long songAlbumID, int duration, String track) {
        this.id = id;
        this.data = data;
        this.songTitle = songTitle;
        this.songAlbum = songAlbum;
        this.songArtist = songArtist;
        this.songAlbumID = songAlbumID;
        this.duration = duration;
        this.track = track;
    }

    protected Song(Parcel in) {
        id = in.readLong();
        data = in.readString();
        songTitle = in.readString();
        songAlbum = in.readString();
        songArtist = in.readString();
        songAlbumID = in.readLong();
        duration = in.readInt();
        track = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public long getSongAlbumID() {
        return songAlbumID;
    }

    public void setSongAlbumID(long songAlbumID) {
        this.songAlbumID = songAlbumID;
    }

    public String getAlbumArt() {
        return ContentUris.withAppendedId(Uri.parse(ALBUM_PATH), songAlbumID).toString();
    }

    public int getDuration() {
        return duration;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.data);
        dest.writeString(this.songTitle);
        dest.writeString(this.songAlbum);
        dest.writeString(this.songArtist);
        dest.writeLong(this.songAlbumID);
        dest.writeInt(this.duration);
        dest.writeString(this.track);
    }

}