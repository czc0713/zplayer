package com.zc.zplayer.emitter;

import android.content.Context;
import android.content.Intent;

public class AudioEmitter {

    public static String BROADCAST_NEW_AUDIO = "MP_BROADCAST_NEW_AUDIO";
    public static String BROADCAST_PAUSE_PLAY = "MP_BROADCAST_PAUSE_AUDIO";


    public static void broadcastNewAudio(Context context){
        Intent broadcastIntent = new Intent(BROADCAST_NEW_AUDIO);
        context.sendBroadcast(broadcastIntent);
    }

    public static void broadcastPausePlay(Context context){
        Intent broadcastIntent = new Intent(BROADCAST_PAUSE_PLAY);
        context.sendBroadcast(broadcastIntent);
    }
}
