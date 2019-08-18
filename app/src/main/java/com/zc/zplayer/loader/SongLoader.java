package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.zc.zplayer.model.Song;

import java.util.ArrayList;

public class SongLoader {
    private final static String BASE_SELECTION = MediaStore.Audio.AudioColumns.IS_MUSIC + "=1";
    private final static Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private final static String[] BASE_PROJECTION = {
        MediaStore.Audio.AudioColumns._ID, //0
        MediaStore.Audio.AudioColumns.DATA, //1
        MediaStore.Audio.AudioColumns.TITLE, //2
        MediaStore.Audio.AudioColumns.ARTIST, //3
        MediaStore.Audio.AudioColumns.ALBUM, //4
        MediaStore.Audio.AudioColumns.ALBUM_ID, //5
        MediaStore.Audio.AudioColumns.DURATION, //6
        MediaStore.Audio.AudioColumns.TRACK //7
    };

    public static ArrayList<Song> getSongs(ContentResolver contentResolver) {
        ArrayList<Song> songList = new ArrayList<>();
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(URI, BASE_PROJECTION, BASE_SELECTION, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String data = cursor.getString(1);
                String title = cursor.getString(2);
                String album = cursor.getString(3);
                String artist = cursor.getString(4);
                long album_id = cursor.getLong(5);
                int duration = cursor.getInt(6);
                int track = cursor.getInt(7);
                songList.add(new Song(id, data, title, album, artist, album_id, duration, track));
            }
            cursor.close();
        }
        return songList;
    }

    public static ArrayList<Song> getSongsFromAlbum(ContentResolver contentResolver, String albumID){
        ArrayList<Song> songList = new ArrayList<>();
        String selection = BASE_SELECTION + " AND " + MediaStore.Audio.AudioColumns.ALBUM_ID + "=" + albumID;
        String sortOrder = MediaStore.Audio.Media.TRACK + " ASC";
        Cursor cursor = contentResolver.query(URI, BASE_PROJECTION, selection, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String data = cursor.getString(1);
                String title = cursor.getString(2);
                String album = cursor.getString(3);
                String artist = cursor.getString(4);
                long album_id = cursor.getLong(5);
                int duration = cursor.getInt(6);
                int track = cursor.getInt(7);
                songList.add(new Song(id, data, title, album, artist, album_id, duration, track));
            }
            cursor.close();
        }
        return songList;
    }

    public static ArrayList<Song> getSongsFromGenre(ContentResolver contentResolver, int genreId){
        ArrayList<Song> songList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", genreId);
        String sortOrder = MediaStore.Audio.Media.TRACK + " ASC";
        Cursor cursor = contentResolver.query(uri, BASE_PROJECTION, BASE_SELECTION, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String data = cursor.getString(1);
                String title = cursor.getString(2);
                String album = cursor.getString(3);
                String artist = cursor.getString(4);
                long album_id = cursor.getLong(5);
                int duration = cursor.getInt(6);
                int track = cursor.getInt(7);
                songList.add(new Song(id, data, title, album, artist, album_id, duration, track));
            }
            cursor.close();
        }
        return songList;
    }
}
