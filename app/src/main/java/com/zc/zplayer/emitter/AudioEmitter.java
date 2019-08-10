package com.zc.zplayer.emitter;

import android.content.Context;
import android.content.Intent;

import static com.zc.zplayer.util.Constants.BROADCAST_NEW_AUDIO;
import static com.zc.zplayer.util.Constants.BROADCAST_PAUSE_PLAY;

public class AudioEmitter {
    public static void broadcastNewAudio(Context context){
        Intent broadcastIntent = new Intent(BROADCAST_NEW_AUDIO);
        context.sendBroadcast(broadcastIntent);
    }

    public static void broadcastPausePlay(Context context){
        Intent broadcastIntent = new Intent(BROADCAST_PAUSE_PLAY);
        context.sendBroadcast(broadcastIntent);
    }
}
