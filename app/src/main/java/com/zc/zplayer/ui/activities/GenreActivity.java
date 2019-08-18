package com.zc.zplayer.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.zc.zplayer.R;
import com.zc.zplayer.adapter.AlbumAdapter;
import com.zc.zplayer.loader.AlbumLoader;
import com.zc.zplayer.model.Album;
import com.zc.zplayer.model.Genre;

import java.util.ArrayList;

import static com.zc.zplayer.util.Constants.SELECTED_GENRE;

public class GenreActivity extends AppCompatActivity {
    private Genre selectedGenre;
    private AlbumAdapter adapter;
    private ArrayList<Album> albumsByGenre;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedGenre = getIntent().getParcelableExtra(SELECTED_GENRE);
        setContentView(R.layout.activity_genre);
        getSupportActionBar().setTitle("Genre");
        initializeViews();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        albumsByGenre = AlbumLoader.getAlbumsByGenre(getContentResolver(), selectedGenre.getId());
        //albumsByGenre = AlbumLoader.getAlbums(getContentResolver());
        adapter = new AlbumAdapter(this, albumsByGenre);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
