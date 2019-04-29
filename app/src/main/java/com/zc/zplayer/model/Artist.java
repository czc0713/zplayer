package com.zc.zplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {
    private long id;
    private String artistTitle;
    private int nbOfTracks;
    private int nbOfAlbums;

    public Artist(long id, String artistTitle, int nbOfTracks, int nbOfAlbums) {
        this.id = id;
        this.artistTitle = artistTitle;
        this.nbOfTracks = nbOfTracks;
        this.nbOfAlbums = nbOfAlbums;
    }

    protected Artist(Parcel in) {
        id = in.readLong();
        artistTitle = in.readString();
        nbOfTracks = in.readInt();
        nbOfAlbums = in.readInt();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtistTitle() {
        return artistTitle;
    }

    public void setArtistTitle(String artistTitle) {
        this.artistTitle = artistTitle;
    }

    public int getNbOfTracks() {
        return nbOfTracks;
    }

    public void setNbOfTracks(int nbOfTracks) {
        this.nbOfTracks = nbOfTracks;
    }

    public int getNbOfAlbums() {
        return nbOfAlbums;
    }

    public void setNbOfAlbums(int nbOfAlbums) {
        this.nbOfAlbums = nbOfAlbums;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(artistTitle);
        dest.writeInt(nbOfTracks);
        dest.writeInt(nbOfAlbums);
    }
}
