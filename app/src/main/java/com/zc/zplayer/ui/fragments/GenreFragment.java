package com.zc.zplayer.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zc.zplayer.R;
import com.zc.zplayer.adapter.GenreAdapter;
import com.zc.zplayer.loader.GenreLoader;
import com.zc.zplayer.model.Genre;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GenreFragment extends Fragment {

    private RecyclerView genreListView;
    private ArrayList<Genre> genreList;
    private ConstraintLayout rootLayout;
    private GenreAdapter adapter;

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
        adapter = new GenreAdapter(getContext(), genreList);
        genreListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        genreListView.setAdapter(adapter);
        return view;
    }
}
