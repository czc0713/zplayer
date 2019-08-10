package com.zc.zplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zc.zplayer.R;
import com.zc.zplayer.adapter.AlbumAdapter;
import com.zc.zplayer.loader.AlbumLoader;
import com.zc.zplayer.model.Album;

import java.util.List;

public class AlbumFragment extends Fragment {

    private RecyclerView albumListView;
    private List<Album> albumList;
    private AlbumLoader loader;
    private AlbumAdapter adapter;
    private boolean isGrid = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.albumList = AlbumLoader.getAlbumList(getActivity().getApplicationContext().getContentResolver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        albumListView = (RecyclerView) view.findViewById(R.id.album_list);
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
