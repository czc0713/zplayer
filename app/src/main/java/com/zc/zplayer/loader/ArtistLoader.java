package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.zc.zplayer.model.Artist;

import java.util.ArrayList;

public class ArtistLoader {
    public static ArrayList<Artist> getArtistList(ContentResolver contentResolver){
        ArrayList<Artist> artistList = new ArrayList<>();
        final Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        final String[] projection = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
        };
        String sortOrder = MediaStore.Audio.Artists.ARTIST + " ASC";
        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String title = cursor.getString(1);
                int nbOfTracks = cursor.getInt(2);
                int nbOfAlbums = cursor.getInt(3);
                artistList.add(new Artist(id, title, nbOfTracks, nbOfAlbums));
            }
            cursor.close();
        }
        return artistList;
    }
}
