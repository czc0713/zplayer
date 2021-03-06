package com.zc.zplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zc.zplayer.R;
import com.zc.zplayer.model.Song;
import com.zc.zplayer.util.FormatTimeUtil;

import java.util.List;

public class AlbumTracksAdapter extends RecyclerView.Adapter<AlbumTracksAdapter.TracksViewHolder> {

    private View view;
    private RecyclerView tracksView;
    private Context context;
    private List<Song> trackList;
    private FormatTimeUtil formatTimeUtil;

    public AlbumTracksAdapter(Context context, List<Song> trackList) {
        this.context = context;
        this.trackList = trackList;
        formatTimeUtil = new FormatTimeUtil();
    }

    @NonNull
    @Override
    public TracksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.item_album_track, viewGroup, false);
        final AlbumTracksAdapter.TracksViewHolder holder = new AlbumTracksAdapter.TracksViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TracksViewHolder tracksViewHolder, int i) {
        tracksViewHolder.trackTitle.setText(trackList.get(i).getSongTitle());
        String songDuration = formatTimeUtil.stringForTime(trackList.get(i).getDuration());
        tracksViewHolder.trackDuration.setText(songDuration);
        tracksViewHolder.trackNo.setText(trackList.get(i).getTrack());
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public class TracksViewHolder extends RecyclerView.ViewHolder {
        private TextView trackTitle;
        private TextView trackNo;
        private TextView trackDuration;

        public TracksViewHolder(@NonNull View itemView) {
            super(itemView);
            trackTitle = itemView.findViewById(R.id.item_track_title);
            trackNo = itemView.findViewById(R.id.item_track_no);
            trackDuration = itemView.findViewById(R.id.item_album_title);


        }
    }
}
