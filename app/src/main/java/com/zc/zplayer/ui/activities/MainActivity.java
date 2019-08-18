package com.zc.zplayer.ui.activities;

import android.Manifest;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zc.zplayer.R;
import com.zc.zplayer.emitter.AudioEmitter;
import com.zc.zplayer.ui.fragments.AlbumFragment;
import com.zc.zplayer.ui.fragments.ArtistFragment;
import com.zc.zplayer.ui.fragments.GenreFragment;
import com.zc.zplayer.ui.fragments.SongFragment;
import com.zc.zplayer.ui.fragments.TabAdapter;
import com.zc.zplayer.model.Song;
import com.zc.zplayer.service.MediaPlayerService;
import com.zc.zplayer.util.StorageUtil;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.zc.zplayer.util.Constants.IS_PLAYING;
import static com.zc.zplayer.util.Constants.NOW_PLAYING;

public class MainActivity extends ServiceActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    private MenuItem prevMenuItem;
    private StorageUtil storageUtil;
    // Music Controller Views
    private ImageButton mPauseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mSongTitle;
    private TextView mArtistTitle;
    private CircleImageView mImage;
    private ConstraintLayout controllerLayout;
    private Song audioSong;
    private RefWatcher refWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        // Normal app init code...
        refWatcher = LeakCanary.install(getApplication());
        checkPermissions();
        checkSavedSettings();
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Music");
        setContentView(R.layout.activity_main);
        initializeViews();
        initializeController();
        registerReceivers();
        LocalBroadcastManager.getInstance(this).registerReceiver(mAudioReceiver,
                new IntentFilter(NOW_PLAYING));
    }

    public static RefWatcher getRefWatcher(Context context) {
        MainActivity application = (MainActivity) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    protected void initializeReceiver() {
        newAudioReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateControllerView(playerService.getActiveAudio());
            }
        };
        pausePlayReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (playerService.isPlaying()){
                    mPauseButton.setImageResource(R.drawable.ic_controller_button_pause);
                }
                else{
                    mPauseButton.setImageResource(R.drawable.ic_controller_button_play);
                }
            }
        };
    }

    public BroadcastReceiver mAudioReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (serviceBound){
                startService(new Intent(getApplicationContext(), MediaPlayerService.class));
                AudioEmitter.broadcastNewAudio(getApplicationContext());
            }

        }
    };

    private void checkSavedSettings(){
        storageUtil = new StorageUtil(this);
        if (storageUtil.isNightModeState()){
            setTheme(R.style.AppThemeDark);
        }
        else{
            setTheme(R.style.AppTheme);
        }
    }

    private void checkSavedSong(){
        audioSong = storageUtil.loadAudio();
        if (audioSong != null){
            updateControllerView(audioSong);
        }
    }

    private void checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void initializeViews(){
        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new SongFragment(), getString(R.string.tracks));
        tabAdapter.addFragment(new AlbumFragment(), getString(R.string.albums));
        tabAdapter.addFragment(new ArtistFragment(), getString(R.string.artists));
        tabAdapter.addFragment(new GenreFragment(), getString(R.string.genres));
        viewPager.setAdapter(tabAdapter);
        navigationView = findViewById(R.id.navigation_bar);
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_songs:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_albums:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_artists:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_genres:
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.item_playlists:
                                break;
                        }
                        return true;
                    }
                }
        );
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null){
                    prevMenuItem.setChecked(false);
                }
                else{
                    navigationView.getMenu().getItem(0).setChecked(false);
                }
                navigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initializeController(){
        controllerLayout = findViewById(R.id.controller_layout);
        mSongTitle = findViewById(R.id.controller_song_title);
        mArtistTitle = findViewById(R.id.controller_song_artist);
        mImage = findViewById(R.id.controller_song_art);
        mPauseButton = findViewById(R.id.controller_play);
        mPrevButton = findViewById(R.id.controller_prev);
        mNextButton = findViewById(R.id.controller_next);
        mSongTitle.setSelected(true);
        mArtistTitle.setSelected(true);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerService.isPlaying()){
                    playerService.pauseMedia();
                    AudioEmitter.broadcastPausePlay(getApplicationContext());
                }
                else{
                    playerService.playMedia();
                    AudioEmitter.broadcastPausePlay(getApplicationContext());
                }
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerService.skipToNext();
                AudioEmitter.broadcastNewAudio(getApplicationContext());
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerService.skipToPrevious();
                AudioEmitter.broadcastNewAudio(getApplicationContext());
            }
        });
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fixme add condition if empty song...
                Intent intent = new Intent(getApplicationContext(), SongActivity.class);
                boolean isPlaying = playerService.isPlaying();
                intent.putExtra(IS_PLAYING, isPlaying);
                startActivity(intent);
            }
        });
        checkSavedSong();
    }

    private void updateControllerView(Song song){
        if (song != null) {
            mSongTitle.setText(song.getSongTitle());
            mArtistTitle.setText(song.getSongArtist());
            Glide.with(getApplicationContext())
                    .load(song.getAlbumArt())
                    .placeholder(R.drawable.ic_default_music)
                    .into(mImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.item_about:
                //TODO About screen?
                break;
            case R.id.item_night_mode:
                if (!storageUtil.isNightModeState()){
                    storageUtil.storeNightModeState(true);
                }
                else{
                    storageUtil.storeNightModeState(false);
                }
                recreate();
                break;
            case R.id.item_search:
                //TODO Search for music
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void recreate(){
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(getIntent());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
