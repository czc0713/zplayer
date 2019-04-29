package com.zc.zplayer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zc.zplayer.emitter.AudioEmitter;
import com.zc.zplayer.service.MediaPlayerService;

public abstract class ServiceActivity extends AppCompatActivity {

    public static final String BROADCAST_PLAY_NEW_AUDIO = "MP_BROADCAST_PLAY_NEW_AUDIO";
    public static final String NOW_PLAYING = "NOW_PLAYING_SONG";
    protected MediaPlayerService playerService;
    protected boolean serviceBound = false;
    protected BroadcastReceiver newAudioReciever;
    protected BroadcastReceiver pausePlayReceiver;

    protected abstract void initializeReceiver();

    protected void registerReceivers(){
        initializeReceiver();
        try{
            registerReceiver(newAudioReciever, new IntentFilter(AudioEmitter.BROADCAST_NEW_AUDIO));
        }
        catch (Exception e) {}
        try{
            registerReceiver(pausePlayReceiver, new IntentFilter(AudioEmitter.BROADCAST_PAUSE_PLAY));
        }
        catch (Exception e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachService();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            playerService = binder.getService();
            serviceBound = true;
            onServiceAttached(playerService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };


    private void attachService() {
        Intent service = new Intent(this, MediaPlayerService.class);
        bindService(service, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    private void detachService() {
        unbindService(serviceConnection);
    }

    protected void onServiceAttached(MediaPlayerService service) {
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(newAudioReciever);
            unregisterReceiver(pausePlayReceiver);
        }
        catch (Exception e){
        }

        if (serviceBound) {
            detachService();
            playerService.stopSelf();
        }
    }
}
