package com.example.shoaib.gomusic;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapters_Miscellenous.customSimpleCursorAdapter;


public class playListFragment extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private Uri playlistUri;
    private ArrayList<singlePlaylistItem> listForPlayLists;
    private SimpleCursorAdapter mListAdapter;
    private ListView mListview;
    private Intent mIntent;

    public playListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistUri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        listForPlayLists= new ArrayList<singlePlaylistItem>();
        getLoaderManager().initLoader(22,null,this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout =inflater.inflate(R.layout.fragment_play_list, container, false);
        mListview = (ListView)layout.findViewById(R.id.playListListView);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mIntent = new Intent(getActivity(),detailedArtistActivity.class);
                mIntent.putExtra("playListID",listForPlayLists.get(position).getPlaylistID());
                mIntent.putExtra("playListName",listForPlayLists.get(position).getPlaylistName());
                mIntent.putExtra("whoCalls","fromPlayListFragment");
                startActivity(mIntent);
            }
        });
        return layout;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),playlistUri,
                new String[]{MediaStore.Audio.Playlists._ID,MediaStore.Audio.Playlists.NAME,MediaStore.Audio.Playlists.DATE_ADDED},null,null,MediaStore.Audio.Playlists.NAME+" ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mListAdapter = new customSimpleCursorAdapter(getActivity(),R.layout.single_playlist_item_layout,data,
                new String[]{MediaStore.Audio.Playlists.NAME,MediaStore.Audio.Playlists.DATE_ADDED},
                new int[]{R.id.PlayListNameText,R.id.playListDateText},0);
        mListview.setAdapter(mListAdapter);

        if (data.moveToFirst()){

            do {
                singlePlaylistItem mPlayListItem = new singlePlaylistItem();
                mPlayListItem.setPlaylistID(data.getInt(data.getColumnIndex(MediaStore.Audio.Playlists._ID)));
                mPlayListItem.setPlaylistName(data.getString(data.getColumnIndex(MediaStore.Audio.Playlists.NAME)));
                mPlayListItem.setDateAdded(data.getString(data.getColumnIndex(MediaStore.Audio.Playlists.DATE_ADDED)));
                listForPlayLists.add(mPlayListItem);
            }
            while(data.moveToNext());

        }
        data.moveToFirst();
        mListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mListAdapter.swapCursor(null);
    }
}
