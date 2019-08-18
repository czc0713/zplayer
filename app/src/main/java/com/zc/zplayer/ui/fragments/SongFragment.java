package com.zc.zplayer.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zc.zplayer.R;
import com.zc.zplayer.adapter.SongAdapter;
import com.zc.zplayer.loader.SongLoader;
import com.zc.zplayer.model.Song;

import java.util.ArrayList;

public class SongFragment extends Fragment {

    private RecyclerView songListView;
    private ArrayList<Song> songList;
    private ConstraintLayout rootLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.songList = SongLoader.getSongs(getActivity().getApplicationContext().getContentResolver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        songListView = (RecyclerView) view.findViewById(R.id.song_list);
        rootLayout = view.findViewById(R.id.container_songs);
        songListView.setNestedScrollingEnabled(true);
        SongAdapter adapter = new SongAdapter(getContext(), songList);
        songListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        songListView.setAdapter(adapter);
        return view;
    }
}

