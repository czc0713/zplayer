package com.zc.zplayer.fragment;

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
import com.zc.zplayer.adapter.ArtistAdapter;
import com.zc.zplayer.loader.ArtistLoader;
import com.zc.zplayer.model.Artist;

import java.util.ArrayList;

public class ArtistFragment extends Fragment {

    private RecyclerView artistListView;
    private ArrayList<Artist> artistList;
    private ConstraintLayout rootLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.artistList = ArtistLoader.getArtistList(getActivity().getApplicationContext().getContentResolver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        artistListView = view.findViewById(R.id.artist_list);
        rootLayout = view.findViewById(R.id.container_artists);
        artistListView.setNestedScrollingEnabled(true);
        ArtistAdapter adapter = new ArtistAdapter(getContext(), artistList);
        artistListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        artistListView.setAdapter(adapter);
        return view;
    }
}
