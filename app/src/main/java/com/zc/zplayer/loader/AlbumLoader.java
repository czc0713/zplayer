package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.zc.zplayer.model.Album;

import java.util.ArrayList;

public class AlbumLoader {
    private final static Uri BASE_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    private final static String[] BASE_PROJECTION = {
            MediaStore.Audio.Albums._ID, //0
            MediaStore.Audio.Albums.ALBUM, //1
            MediaStore.Audio.Albums.ARTIST, //2
            MediaStore.Audio.Albums.ALBUM_ART, //3
            MediaStore.Audio.Albums.NUMBER_OF_SONGS //4
    };

    private static Album getAlbumFromCursor(Cursor cursor){
        String album_id = cursor.getString(0);
        String album_title = cursor.getString(1);
        String album_artist = cursor.getString(2);
        String album_art = cursor.getString(3);
        String album_tracks = cursor.getString(4);
        return new Album(album_id, album_title, album_artist, album_art);
    }


    public static ArrayList<Album> getAlbums(ContentResolver contentResolver) {
        ArrayList<Album> albumList = new ArrayList<>();
        String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        Cursor cursor = contentResolver.query(BASE_URI, BASE_PROJECTION, null, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                albumList.add(getAlbumFromCursor(cursor));
            }
            cursor.close();
        }
        return albumList;
    }

    public static ArrayList<Album> getAlbumsByGenre(ContentResolver contentResolver, int genreId) {
        ArrayList<Album> albumList = new ArrayList<>();

        String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        Cursor cursor = contentResolver.query(BASE_URI, BASE_PROJECTION, null, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                albumList.add(getAlbumFromCursor(cursor));
            }
            cursor.close();
        }
        return albumList;
    }
}
