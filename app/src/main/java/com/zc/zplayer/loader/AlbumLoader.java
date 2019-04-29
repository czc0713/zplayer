package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.zc.zplayer.model.Album;

import java.util.ArrayList;

public class AlbumLoader {
    private final static Uri URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    private final static String[] PROJECTION = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS
    };

    public static ArrayList<Album> getAlbumList(ContentResolver contentResolver) {
        ArrayList<Album> albumList = new ArrayList<>();

        final Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        final String _id = MediaStore.Audio.Albums._ID;
        final String _name = MediaStore.Audio.Albums.ALBUM;
        final String _artist = MediaStore.Audio.Albums.ARTIST;
        final String _albumart = MediaStore.Audio.Albums.ALBUM_ART;
        final String _tracks = MediaStore.Audio.Albums.NUMBER_OF_SONGS;

        String[] projection = new String[] { _id, _name, _artist, _albumart, _tracks };
        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = MediaStore.Audio.Albums.ALBUM + " ASC";

        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String album_id = cursor.getString(cursor.getColumnIndex(_id));
                String album_title = cursor.getString(cursor.getColumnIndex(_name));
                String album_artist = cursor.getString(cursor.getColumnIndex(_artist));
                String album_art = cursor.getString(cursor.getColumnIndex(_albumart));
                String album_tracks = cursor.getString(cursor.getColumnIndex(_tracks));
                albumList.add(new Album(album_id, album_title, album_artist, album_art));
            }
        }
        cursor.close();
        return albumList;
    }
}
