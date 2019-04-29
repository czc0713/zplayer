package com.zc.zplayer.service;

import android.content.Context;
import android.media.MediaPlayer;

import com.zc.zplayer.emitter.AudioEmitter;
import com.zc.zplayer.model.Song;
import com.zc.zplayer.util.StorageUtil;

import java.util.ArrayList;

public class MusicPlayer extends MediaPlayer {
    private int resumePosition;
    private Context context;
    private int audioIndex;
    private Song activeAudio;
    private ArrayList<Song> audioList;

    public void skipToNext() {
        if (audioIndex == audioList.size() - 1) {
            //if last in playlist
            audioIndex = 0;
            activeAudio = audioList.get(audioIndex);
        } else {
            //get next in playlist
            activeAudio = audioList.get(++audioIndex);
        }
        //Update stored index
        new StorageUtil(context).storeAudioIndex(audioIndex);
        stop();
        reset();
        //initMediaPlayer();
    }

    public void skipToPrevious() {
        if (audioIndex == 0) {
            //if first in playlist
            //set index to the last of audioList
            audioIndex = audioList.size() - 1;
            activeAudio = audioList.get(audioIndex);
        } else {
            //get previous in playlist
            activeAudio = audioList.get(--audioIndex);
        }

        //Update stored index
        new StorageUtil(context).storeAudioIndex(audioIndex);

        stop();
        //reset player
        reset();
        //initMediaPlayer();
    }



    public void resume() throws IllegalStateException{
        if (isPlaying()) {
            seekTo(resumePosition);
            start();
            AudioEmitter.broadcastPausePlay(context);
        }
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        if (isPlaying()) {
            stop();
        }
    }

    @Override
    public void pause() throws IllegalStateException {
        if (isPlaying()){
            pause();
            resumePosition = getCurrentPosition();
            AudioEmitter.broadcastPausePlay(context);
        }
    }


}
