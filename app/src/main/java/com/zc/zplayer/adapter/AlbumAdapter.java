package com.zc.zplayer.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.zc.zplayer.model.Album;
import com.zc.zplayer.ui.activities.AlbumActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.zc.zplayer.util.Constants.SELECTED_ALBUM;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private View view;
    private Context context;
    private List<Album> albumList;

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    public AlbumAdapter(Context context, List<Album> albumList, boolean isDark) {
        this.context = context;
        this.albumList = albumList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.item_album, viewGroup, false);
        final AlbumViewHolder holder = new AlbumViewHolder(view);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                Album selectedAlbum = albumList.get(index);
                Intent intent = new Intent(context, AlbumActivity.class);
                intent.putExtra(SELECTED_ALBUM, selectedAlbum);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int i) {
        albumViewHolder.albumImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_translate));
        albumViewHolder.albumTitleText.setText(albumList.get(i).getAlbumTitle());
        albumViewHolder.albumArtistText.setText(albumList.get(i).getAlbumArtist());
        albumViewHolder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));

        Glide.with(view)
                .load(albumList.get(i).getAlbumArt())
                .placeholder(R.drawable.music_default_cover_img)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(albumViewHolder.albumImage);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        private TextView albumTitleText;
        private TextView albumArtistText;
        private CircleImageView albumImage;
        private RelativeLayout container;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_album_card);
            albumTitleText = itemView.findViewById(R.id.item_album_title);
            albumArtistText = itemView.findViewById(R.id.item_album_artist);
            albumImage = itemView.findViewById(R.id.item_album_art);
        }

    }
}
