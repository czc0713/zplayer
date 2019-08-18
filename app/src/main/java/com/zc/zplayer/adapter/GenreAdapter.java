package com.zc.zplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zc.zplayer.R;
import com.zc.zplayer.model.Genre;
import com.zc.zplayer.ui.activities.GenreActivity;

import java.util.ArrayList;

import static com.zc.zplayer.util.Constants.SELECTED_GENRE;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private ArrayList<Genre> genreList;
    private View view;
    private Context context;

    public GenreAdapter(Context context, ArrayList<Genre> genreList) {
        this.genreList = genreList;
        this.context = context;
    }

    @NonNull
    @Override
    public GenreAdapter.GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.item_genre, viewGroup, false);
        final GenreAdapter.GenreViewHolder holder = new GenreAdapter.GenreViewHolder(view);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                Genre selectedGenre = genreList.get(index);
                Intent intent = new Intent(context, GenreActivity.class);
                intent.putExtra(SELECTED_GENRE, selectedGenre);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.GenreViewHolder genreViewHolder, int i) {
        genreViewHolder.genreText.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_translate));
        genreViewHolder.genreText.setText(genreList.get(i).getGenreTitle());
        int nbOfTracks = genreList.get(i).getCount();
        String trackText = "track";
        if (nbOfTracks > 1){
            trackText = "tracks";
        }
        genreViewHolder.nbOfTracksText.setText(nbOfTracks + " " + trackText);
        genreViewHolder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        private TextView genreText;
        private TextView nbOfTracksText;
        private RelativeLayout container;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreText = itemView.findViewById(R.id.item_genre_title);
            nbOfTracksText = itemView.findViewById(R.id.item_genre_nb_tracks);
            container = itemView.findViewById(R.id.item_genre_card);
        }
    }
}
