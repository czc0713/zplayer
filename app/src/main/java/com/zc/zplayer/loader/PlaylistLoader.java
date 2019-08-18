package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.zc.zplayer.model.Playlist;

import java.util.ArrayList;

public class PlaylistLoader {

    public static ArrayList<Playlist> getPlaylist(ContentResolver contentResolver){
        ArrayList<Playlist> playlistArray = new ArrayList();
        final Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        final String[] projection = {
                MediaStore.Audio.Playlists._ID,
                MediaStore.Audio.Playlists._COUNT,
                MediaStore.Audio.Playlists.DATA,
                MediaStore.Audio.Playlists.NAME
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Audio.Playlists.NAME + " ASC";
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                int count = cursor.getInt(1);
                String data = cursor.getString(2);
                String name = cursor.getString(3);
                playlistArray.add(new Playlist(id, count, data, name));
            }
            cursor.close();
        }
        return playlistArray;
    }
}
