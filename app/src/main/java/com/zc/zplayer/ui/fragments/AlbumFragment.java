package com.zc.zplayer.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zc.zplayer.R;
import com.zc.zplayer.adapter.AlbumAdapter;
import com.zc.zplayer.loader.AlbumLoader;
import com.zc.zplayer.model.Album;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumFragment extends Fragment {

    private RecyclerView albumListView;
    private List<Album> albumList;
    private AlbumLoader loader;
    private AlbumAdapter adapter;
    private boolean isGrid = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.albumList = AlbumLoader.getAlbums(getActivity().getApplicationContext().getContentResolver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        albumListView = view.findViewById(R.id.album_list);
        albumListView.setNestedScrollingEnabled(true);

        adapter = new AlbumAdapter(getContext(), albumList);
        albumListView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        if (isGrid){
//            albumListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        }
        albumListView.setAdapter(adapter);

        return view;
    }
}
