package com.zc.zplayer.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zc.zplayer.R;
import com.zc.zplayer.adapter.SongAdapter;
import com.zc.zplayer.loader.SongLoader;
import com.zc.zplayer.model.Song;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SongFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Song> songList;
    private RelativeLayout rootLayout;
    private TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.songList = SongLoader.getSongs(getActivity().getApplicationContext().getContentResolver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView = view.findViewById(R.id.song_list);
        rootLayout = view.findViewById(R.id.container_songs);
        textView = view.findViewById(R.id.empty_songs_text);
        recyclerView.setNestedScrollingEnabled(true);
        if (songList.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            SongAdapter adapter = new SongAdapter(getContext(), songList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}

