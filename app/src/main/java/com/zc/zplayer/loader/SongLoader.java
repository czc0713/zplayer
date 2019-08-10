package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.zc.zplayer.model.Song;

import java.util.ArrayList;

public class SongLoader {
    private final static Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private final static String[] PROJECTION = {
        MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TRACK
    };

    public static ArrayList<Song> getSongList(ContentResolver contentResolver) {
        ArrayList<Song> songList = new ArrayList<>();
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(URI, PROJECTION, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String data = cursor.getString(1);
                String title = cursor.getString(2);
                String album = cursor.getString(3);
                String artist = cursor.getString(4);
                long album_id = cursor.getLong(5);
                int duration = cursor.getInt(6);
                String track = cursor.getString(7);
                songList.add(new Song(id, data, title, album, artist, album_id, duration, track));
            }
        }
        cursor.close();
        return songList;
    }

    public static ArrayList<Song> getSongListFromAlbum(ContentResolver contentResolver, String albumID){
        ArrayList<Song> songList = new ArrayList<>();
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0 AND " + MediaStore.Audio.Media.ALBUM_ID + "=" + albumID;
        String sortOrder = MediaStore.Audio.Media.TRACK + " ASC";
        Cursor cursor = contentResolver.query(URI, PROJECTION, selection, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String data = cursor.getString(1);
                String title = cursor.getString(2);
                String album = cursor.getString(3);
                String artist = cursor.getString(4);
                long album_id = cursor.getLong(5);
                int duration = cursor.getInt(6);
                String track = cursor.getString(7);
                songList.add(new Song(id, data, title, album, artist, album_id, duration, track));
            }
        }
        cursor.close();
        return songList;
    }

}
