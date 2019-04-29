package com.zc.zplayer.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zc.zplayer.model.Song;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageUtil {

    private final String STORAGE = "com.zc.player.MP_STORAGE_UTIL";
    private final String KEY_NIGHT_MODE = "NIGHT_MODE";
    private SharedPreferences preferences;
    private Context context;

    public StorageUtil(Context context) {
        preferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void storeNightModeState(boolean state){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_NIGHT_MODE, state);
        editor.commit();
    }

    public boolean loadNightModeState(){
        Boolean state = preferences.getBoolean(KEY_NIGHT_MODE, false);
        return state;
    }

    public void storeAudioList(ArrayList<Song> arrayList) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString("audioArrayList", json);
        editor.apply();
    }

    public ArrayList<Song> loadAudioList() {
        Gson gson = new Gson();
        String json = preferences.getString("audioArrayList", null);
        Type type = new TypeToken<ArrayList<Song>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void storeAudio(Song song){
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(song);
        editor.putString("audio", json);
        editor.apply();
    }

    public Song loadAudio(){
        Gson gson = new Gson();
        String json = preferences.getString("audio", null);
        Type type = new TypeToken<Song>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void storeAudioIndex(int index) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("audioIndex", index);
        editor.apply();
    }

    public int loadAudioIndex() {
        return preferences.getInt("audioIndex", -1);//return -1 if no data found
    }

    public void clearCachedAudioPlaylist() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
