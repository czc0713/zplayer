package com.zc.zplayer.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.widget.NestedScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zc.zplayer.R;
import com.zc.zplayer.adapter.AlbumTracksAdapter;
import com.zc.zplayer.loader.SongLoader;
import com.zc.zplayer.model.Album;
import com.zc.zplayer.model.Song;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.zc.zplayer.util.Constants.SELECTED_ALBUM;

public class AlbumActivity extends AppCompatActivity {

    private ImageView albumImageBanner;
    private CircleImageView albumImageProfile;
    private TextView albumTitle, albumArtist;
    private ConstraintLayout rootLayout;
    private ConstraintSet layout1, layout2;
    private boolean isOpen = false;
    private RecyclerView tracksView;
    private AlbumTracksAdapter adapter;
    private ArrayList<Song> albumTracks;
    private Album selectedAlbum;
    private ConstraintLayout constraintLayout;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();

        scrollView = findViewById(R.id.scrollView);
        rootLayout = findViewById(R.id.selected_album_layout);
        constraintLayout = findViewById(R.id.selected_album_layout);
        albumImageBanner = findViewById(R.id.selected_album_cover);
        albumImageProfile = findViewById(R.id.selected_album_photo);
        albumTitle = findViewById(R.id.selected_album_title);
        albumArtist = findViewById(R.id.selected_album_artist);


        selectedAlbum =  getIntent().getParcelableExtra(SELECTED_ALBUM);
        albumTracks = SongLoader.getSongsFromAlbum(getContentResolver(), selectedAlbum.getId());

        tracksView = findViewById(R.id.selected_album_content);
        tracksView.setNestedScrollingEnabled(false);

        adapter = new AlbumTracksAdapter(this, albumTracks);
        tracksView.setLayoutManager(new LinearLayoutManager(this));
        tracksView.setAdapter(adapter);
        initializeViews();
    }

    private void initializeViews() {
        Glide.with(this)
                .load(selectedAlbum.getAlbumArt())
                .placeholder(R.drawable.ic_default_music)
                .into(albumImageProfile);
        Glide.with(this)
                .load(selectedAlbum.getAlbumArt())
                .centerCrop()
                .placeholder(R.color.black)
                .into(albumImageBanner);

        albumTitle.setText(selectedAlbum.getAlbumTitle());
        albumArtist.setText(selectedAlbum.getAlbumArtist());

        layout2.clone(this, R.layout.album_expanded);
        layout1.clone(rootLayout);

        //setBlurryBackground(selectedAlbum.getAlbumArt());
    }

    private void setBlurryBackground(String image) {
        Glide.with(this)
                .load(image)
                .apply(bitmapTransform(new BlurTransformation(25, 8)))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        scrollView.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {

                    }
                });
    }
}
