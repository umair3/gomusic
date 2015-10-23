package com.example.shoaib.gomusic;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.ContentResolver;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters_Miscellenous.customSimpleCursorAdapter;
import adapters_Miscellenous.songsListRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link songsFragment
 * to handle interaction events.
 */
public class songsFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


//    private RecyclerView recyclerView;
//    private static ArrayList<singleSongItem> songsList;
//    songsListRecyclerAdapter madapter;
//    private ContentResolver contentResolver;

    private Uri songsUri;
    private List<singleSongItem> songsList;
    private ListView mListView;
    private customSimpleCursorAdapter mSimpleCursorAdapter;

    public songsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_songs, container, false);

        songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songsList= new ArrayList<singleSongItem>();
        getLoaderManager().initLoader(10, null, this);
        mListView = (ListView) layout.findViewById(R.id.songsListView);


//        songsList= new ArrayList<singleSongItem>();
//        recyclerView = (RecyclerView)layout.findViewById(R.id.songsList);
//        contentResolver = getActivity().getContentResolver();
//        getData();
//        madapter = new songsListRecyclerAdapter(getActivity(),songsList);
//        recyclerView.setAdapter(madapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;

    }

//    public void getData () {
//
//        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String orderBy = android.provider.MediaStore.Audio.Media.TITLE;
//        Cursor musicCursor = contentResolver.query(musicUri,null,null,null,orderBy);
//
//        if(musicCursor!=null && musicCursor.moveToFirst()){
//            //get columns
//
//            int idColumn = musicCursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media._ID);
//            int titleColumn = musicCursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media.TITLE);
//            int artistColumn = musicCursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media.ARTIST);
//
//
//            //add songs to list
//            do {
//                long thisId = musicCursor.getLong(idColumn);
//                String thisTitle = musicCursor.getString(titleColumn);
//                String thisArtist = musicCursor.getString(artistColumn);
//                songsList.add(new singleSongItem(thisId, thisTitle, thisArtist));
//            }
//            while (musicCursor.moveToNext());
//        }
//
//
//    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity(),songsUri,new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
                                MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION},null,null,MediaStore.Audio.Media.TITLE+" ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mSimpleCursorAdapter = new customSimpleCursorAdapter(getActivity(),R.layout.singlesong_proper_item,data,
                new String[]{"TITLE","ARTIST","DURATION"}, new int []{R.id.songTitleProper,R.id.songArtistProper,R.id.songDuration},0);

        mListView.setAdapter(mSimpleCursorAdapter);

        if (data.moveToFirst()) {

            singleSongItem tempSongItem = new singleSongItem();
            do {
                tempSongItem.setSongID(data.getLong(data.getColumnIndex("_ID")));
                tempSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
                tempSongItem.setSongArtistTile(data.getString(data.getColumnIndex("ARTIST")));
                tempSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
                //tempSongItem.setSongIconId(data.getString(data.getColumnIndex("album_art")));
                songsList.add(tempSongItem);

            }
            while (data.moveToNext());
        }

        mSimpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mSimpleCursorAdapter.swapCursor(null);
    }
}
