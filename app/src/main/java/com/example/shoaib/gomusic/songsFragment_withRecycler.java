package com.example.shoaib.gomusic;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapters_Miscellenous.DividerItemDecoration;
import adapters_Miscellenous.recyclerAdapter;


public class songsFragment_withRecycler extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri songsUri;
    private ArrayList<singleSongItem> songsList;
    RecyclerView mSongsRecyclerList;
    recyclerAdapter mSongsListAdapter;
    private Intent mIntent;


    public songsFragment_withRecycler() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songsList= new ArrayList<singleSongItem>();
        getLoaderManager().initLoader(10, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.songs_fragment_with_recycler, container, false);
        mSongsRecyclerList = (RecyclerView) layout.findViewById(R.id.songsRecyclerList);
        mSongsListAdapter = new recyclerAdapter(getActivity(),songsList);
        mSongsRecyclerList.setAdapter(mSongsListAdapter);
        mSongsRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mSongsRecyclerList.addItemDecoration(itemDecoration);

        return layout;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity(),songsUri,new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION},null,null,MediaStore.Audio.Media.TITLE+" ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        if (data.moveToFirst()) {

            singleSongItem tempSongItem;
            do {
                tempSongItem = new singleSongItem();
                tempSongItem.setSongID(data.getLong(data.getColumnIndex("_ID")));
                tempSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
                tempSongItem.setSongArtistTile(data.getString(data.getColumnIndex("ARTIST")));
                tempSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
                songsList.add(tempSongItem);
                tempSongItem = null;

            }
            while (data.moveToNext());
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
