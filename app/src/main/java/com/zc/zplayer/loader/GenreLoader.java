package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.zc.zplayer.model.Genre;

import java.util.ArrayList;

public class GenreLoader {

    public static ArrayList<Genre> getGenreList(ContentResolver contentResolver) {
        ArrayList<Genre> genreList = new ArrayList<>();
        final Uri uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
        final String[] projection = {
                MediaStore.Audio.Genres._ID,
                MediaStore.Audio.Genres.NAME
        };
        String sortOrder = MediaStore.Audio.Genres.NAME + " ASC";
        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int count = SongLoader.getSongsFromGenre(contentResolver, id).size();
                genreList.add(new Genre(id, name, count));
            }
            cursor.close();
        }
        return genreList;
    }
}
