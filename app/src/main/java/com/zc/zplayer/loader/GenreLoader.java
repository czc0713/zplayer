package com.zc.zplayer.loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.zc.zplayer.model.Genre;
import java.util.ArrayList;

public class GenreLoader {

    public static ArrayList<Genre> getGenreList(ContentResolver contentResolver){
        ArrayList<Genre> genreList = new ArrayList<>();
        final Uri uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
        final String[] projection = {
                MediaStore.Audio.Genres._ID,
                MediaStore.Audio.Genres.NAME
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Audio.Genres.NAME + " ASC";
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                genreList.add(new Genre(id, 0, name));
            }
        }
        cursor.close();
        return genreList;
    }
}
