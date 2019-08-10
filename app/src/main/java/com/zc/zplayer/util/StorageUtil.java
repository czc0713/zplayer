package com.zc.zplayer.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zc.zplayer.model.Song;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.zc.zplayer.util.Constants.AUDIO;
import static com.zc.zplayer.util.Constants.AUDIO_ARRAY_LIST;
import static com.zc.zplayer.util.Constants.AUDIO_INDEX;
import static com.zc.zplayer.util.Constants.KEY_NIGHT_MODE;
import static com.zc.zplayer.util.Constants.STORAGE_FILE_NAME;

public class StorageUtil {
    private SharedPreferences preferences;

    public StorageUtil(Context context) {
        preferences = context.getSharedPreferences(STORAGE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void storeNightModeState(boolean state){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_NIGHT_MODE, state);
        editor.apply();
    }

    public boolean loadNightModeState(){
        return preferences.getBoolean(KEY_NIGHT_MODE, false);
    }

    public void storeAudioList(ArrayList<Song> arrayList) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(AUDIO_ARRAY_LIST, json);
        editor.apply();
    }

    public ArrayList<Song> loadAudioList() {
        Gson gson = new Gson();
        String json = preferences.getString(AUDIO_ARRAY_LIST, null);
        Type type = new TypeToken<ArrayList<Song>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void storeAudio(Song song){
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(song);
        editor.putString(AUDIO, json);
        editor.apply();
    }

    public Song loadAudio(){
        Gson gson = new Gson();
        String json = preferences.getString(AUDIO, null);
        Type type = new TypeToken<Song>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void storeAudioIndex(int index) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AUDIO_INDEX, index);
        editor.apply();
    }

    public int loadAudioIndex() {
        return preferences.getInt(AUDIO_INDEX, -1);//return -1 if no data found
    }

    public void clearCachedAudioPlaylist() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
