package com.zc.zplayer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zc.zplayer.R;
import com.zc.zplayer.model.Song;
import com.zc.zplayer.util.FormatTimeUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumTracksAdapter extends RecyclerView.Adapter<AlbumTracksAdapter.TracksViewHolder> {

    private View view;
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
        Song track = trackList.get(i);
        tracksViewHolder.trackTitle.setText(track.getSongTitle());
        String songDuration = formatTimeUtil.stringForTime(track.getDuration());
        tracksViewHolder.trackDuration.setText(songDuration);
        Log.d("TRACK NO. ", "" + track.getTrack());
        tracksViewHolder.trackNo.setText(String.valueOf(track.getTrack()));
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
            trackDuration = itemView.findViewById(R.id.item_track_duration);
        }
    }
}
