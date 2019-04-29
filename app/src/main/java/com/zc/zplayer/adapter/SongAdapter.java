package com.zc.zplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zc.zplayer.R;
import com.zc.zplayer.model.SongList;
import com.zc.zplayer.model.Song;
import com.zc.zplayer.util.StorageUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private StorageUtil storageUtil;
    private View view;
    private Context context;
    private ArrayList<Song> songList;
    private SongList playlist;
    public static final String NOW_PLAYING = "NOW_PLAYING_SONG";

    public SongAdapter(Context context, ArrayList<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.item_song, viewGroup, false);
        final SongViewHolder holder = new SongViewHolder(view);
        storageUtil = new StorageUtil(context);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                Song songPicked = songList.get(index);
                // store song picked
                storageUtil.storeAudio(songPicked);
                storageUtil.storeAudioList(songList);
                storageUtil.storeAudioIndex(index);
                Intent intent = new Intent(NOW_PLAYING);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder songViewHolder, int i) {
        songViewHolder.albumImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_translate));
        songViewHolder.titleText.setText(songList.get(i).getSongTitle());
        songViewHolder.artistText.setText(songList.get(i).getSongArtist());
        songViewHolder.albumText.setText(songList.get(i).getSongAlbum());
        songViewHolder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        Glide.with(view)
                .load(songList.get(i).getAlbumArt())
                .placeholder(R.drawable.music_default_cover_img)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(songViewHolder.albumImage);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder{

        private TextView titleText;
        private TextView artistText;
        private TextView albumText;
        private CircleImageView albumImage;
        private RelativeLayout container;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            container = (RelativeLayout) itemView.findViewById(R.id.item_song_card);
            titleText = (TextView) itemView.findViewById(R.id.item_album_title);
            artistText = (TextView) itemView.findViewById(R.id.item_song_artist);
            albumText = (TextView) itemView.findViewById(R.id.item_song_album);
            albumImage = (CircleImageView) itemView.findViewById(R.id.item_album_art);
        }
    }
}
