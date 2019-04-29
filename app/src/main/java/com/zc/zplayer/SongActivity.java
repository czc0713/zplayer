package com.zc.zplayer;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;;
import com.zc.zplayer.emitter.AudioEmitter;
import com.zc.zplayer.model.Song;
import com.zc.zplayer.service.MediaPlayerService;
import com.zc.zplayer.util.FormatTimeUtil;
import java.util.ArrayList;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class SongActivity extends ServiceActivity {

    // Views
    private TextView title;
    private TextView artist;
    private TextView songDuration;
    private TextView songElapsedTime;
    private CircleImageView albumArt;
    private ImageButton pauseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private SeekBar seekBar;
    private ConstraintLayout constraintLayout;
    private Thread updateSeekBar;
    private ArrayList<Song> songList;
    private FormatTimeUtil formatTimeUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_song);
        title = (TextView) findViewById(R.id.current_song_title);
        title.setSelected(true);
        artist = (TextView) findViewById(R.id.current_song_artist);
        albumArt = (CircleImageView) findViewById(R.id.current_song_art);
        pauseButton = (ImageButton) findViewById(R.id.button_pause);
        prevButton = (ImageButton) findViewById(R.id.button_previous);
        nextButton = (ImageButton) findViewById(R.id.button_next);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        songDuration = (TextView) findViewById(R.id.song_duration);
        songElapsedTime = (TextView) findViewById(R.id.song_elapsed_time);
        constraintLayout = (ConstraintLayout) findViewById(R.id.song_layout);
        formatTimeUtil = new FormatTimeUtil();
        boolean isPlaying = (Boolean) getIntent().getBooleanExtra("isPlaying", false);
        if (!isPlaying){
            pauseButton.setImageResource(R.drawable.ic_controller_button_play);
        }
        registerReceivers();
    }

    @Override
    protected void initializeReceiver() {
        newAudioReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateSongInfo();
            }
        };
        pausePlayReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setPausePlayButton();
            }
        };
    }

    private void setPausePlayButton(){
        if (playerService.isPlaying()){
            pauseButton.setImageResource(R.drawable.ic_media_button_pause);
        }
        else{
            pauseButton.setImageResource(R.drawable.ic_media_button_play);
        }
    }

    @Override
    protected void onServiceAttached(final MediaPlayerService service) {
        setSongInfo(service);
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = service.getDuration();
                int currentPosition = service.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                while (currentPosition < totalDuration){
                    try{
                        sleep(1000);
                        currentPosition = service.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        seekBar.setMax(service.getDuration());
        updateSeekBar.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                songElapsedTime.setText(formatTimeUtil.stringForTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                service.seek(seekBar.getProgress());
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerService.isPlaying()){
                    playerService.pauseMedia();
                }
                else{
                    playerService.resumeMedia();
                }
                AudioEmitter.broadcastPausePlay(getApplicationContext());
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerService.skipToPrevious();
                AudioEmitter.broadcastNewAudio(getApplicationContext());
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerService.skipToNext();
                AudioEmitter.broadcastNewAudio(getApplicationContext());
            }
        });
    }

    private void updateElapsedTime(int elapsedTime){
        songElapsedTime.setText(String.valueOf(elapsedTime));
    }

    private void setSongInfo(MediaPlayerService service){
        Song currentSong = service.getActiveAudio();
        title.setText(currentSong.getSongTitle());
        artist.setText(currentSong.getSongAlbum());
        String duration = formatTimeUtil.stringForTime(currentSong.getDuration());
        songDuration.setText(duration);
        setSongImage(currentSong.getAlbumArt());
        setBlurryBackground(currentSong.getAlbumArt());
        startService(new Intent(this, MediaPlayerService.class));
    }

    private void updateSongInfo(){
        Song currentSong = playerService.getActiveAudio();
        title.setText(currentSong.getSongTitle());
        artist.setText(currentSong.getSongArtist());

        String duration = formatTimeUtil.stringForTime(currentSong.getDuration());
        songDuration.setText(duration);
        setSongImage(currentSong.getAlbumArt());
        setBlurryBackground(currentSong.getAlbumArt());

        constraintLayout.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));

        startService(new Intent(this, MediaPlayerService.class));
    }


    private void setSongImage(String image){
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.ic_default_music)
                .into(albumArt);
    }

    private void setBlurryBackground(String image){
        Glide.with(this)
                .load(image)
                .apply(bitmapTransform(new BlurTransformation(25,8)))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        constraintLayout.setBackground(resource);
                    }
                    @Override
                    public void onLoadCleared(Drawable placeholder) {

                    }
                });
    }

}
