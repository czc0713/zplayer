package com.zc.zplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zc.zplayer.R;
import com.zc.zplayer.model.Artist;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {
    private ArrayList<Artist> artistList;
    private View view;
    private Context context;

    public ArtistAdapter(Context context, ArrayList<Artist> artistList) {
        this.artistList = artistList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.item_artist, viewGroup, false);
        final ArtistAdapter.ArtistViewHolder holder = new ArtistAdapter.ArtistViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder artistViewHolder, int i) {
        artistViewHolder.artistText.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_translate));
        artistViewHolder.artistText.setText(artistList.get(i).getArtistTitle());
        int nbOfAlbums = artistList.get(i).getNbOfAlbums();
        int nbOfTracks = artistList.get(i).getNbOfTracks();
        String albumText = "album";
        String trackText = "track";
        if (nbOfAlbums > 1){
            albumText = "albums";
        }
        if (nbOfTracks > 1){
            trackText = "tracks";
        }
        artistViewHolder.nbOfAlbumsText.setText(nbOfAlbums + " " + albumText + " | ");
        artistViewHolder.nbOfTracksText.setText(nbOfTracks + " " + trackText);
        artistViewHolder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        private TextView artistText;
        private TextView nbOfTracksText;
        private TextView nbOfAlbumsText;
        private RelativeLayout container;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistText = itemView.findViewById(R.id.item_artist_title);
            nbOfTracksText = itemView.findViewById(R.id.item_artist_nb_tracks);
            nbOfAlbumsText = itemView.findViewById(R.id.item_artist_nb_albums);
            container = itemView.findViewById(R.id.item_artist_card);
        }
    }
}
