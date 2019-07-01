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
import com.zc.zplayer.adapter.GenreAdapter;
import com.zc.zplayer.loader.GenreLoader;
import com.zc.zplayer.model.Genre;

import java.util.ArrayList;

public class GenreFragment extends Fragment {

    private RecyclerView genreListView;
    private ArrayList<Genre> genreList;
    private ConstraintLayout rootLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.genreList = GenreLoader.getGenreList(getActivity().getApplicationContext().getContentResolver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        genreListView = view.findViewById(R.id.genre_list);
        rootLayout = view.findViewById(R.id.container_genres);
        genreListView.setNestedScrollingEnabled(true);
        GenreAdapter adapter = new GenreAdapter(getContext(), genreList);
        genreListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        genreListView.setAdapter(adapter);
        return view;
    }
}
