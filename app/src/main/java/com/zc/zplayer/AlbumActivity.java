package com.zc.zplayer;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zc.zplayer.adapter.AlbumTracksAdapter;
import com.zc.zplayer.model.SongList;
import com.zc.zplayer.model.Song;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class AlbumActivity extends AppCompatActivity {

    private ImageView albumImageBanner;
    private CircleImageView albumImageProfile;
    private TextView albumTitle, albumArtist;
    private ConstraintLayout rootLayout;
    private ConstraintSet layout1, layout2;
    private boolean isOpen = false;
    private RecyclerView tracksView;
    private AlbumTracksAdapter adapter;
    private SongList playlist;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();

        albumImageBanner = findViewById(R.id.selected_album_cover);
        albumImageProfile = findViewById(R.id.selected_album_photo);
        albumTitle = findViewById(R.id.selected_album_title);
        albumArtist = findViewById(R.id.selected_album_artist);
        rootLayout = findViewById(R.id.selected_album_layout);
        constraintLayout = findViewById(R.id.selected_album_layout);
        //ScrollView scrollView = findViewById(R.id.scrollView);

        playlist = (SongList) getIntent().getParcelableExtra("selected_tracks");

        tracksView = findViewById(R.id.selected_album_content);
        tracksView.setNestedScrollingEnabled(true);

        adapter = new AlbumTracksAdapter(this, playlist.getSongs());
        tracksView.setLayoutManager(new LinearLayoutManager(this));
        tracksView.setAdapter(adapter);

        // Get first song to set album details
        Song song = playlist.getSongs().get(0);
        // Album Details
        Glide.with(this)
                .load(song.getAlbumArt())
                .placeholder(R.drawable.ic_default_music)
                .into(albumImageProfile);
        Glide.with(this)
                .load(song.getAlbumArt())
                .centerCrop()
                .placeholder(R.color.black)
                .into(albumImageBanner);

        //setBlurryBackground(song.getAlbumArt());

        albumTitle.setText(song.getSongAlbum());
        albumArtist.setText(song.getSongArtist());

        layout2.clone(this, R.layout.album_expanded);
        layout1.clone(rootLayout);

    }

    private void setBlurryBackground(String image) {
        Glide.with(this)
                .load(image)
                .apply(bitmapTransform(new BlurTransformation(25, 8)))
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
