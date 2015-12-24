package com.example.shoaib.gomusic;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters_Miscellenous.customSimpleCursorAdapter;


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
    private ArrayList<singleSongItem> songsList;
    private ListView mListView;
    private customSimpleCursorAdapter mSimpleCursorAdapter;
    private Intent mIntent;

    public songsFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putParcelable("TheState",mListView.onSaveInstanceState());
//       // state=mListView.onSaveInstanceState();
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_songs, container, false);


        songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        songsList= new ArrayList<singleSongItem>();
        getLoaderManager().initLoader(10, null, this);
        mListView = (ListView) layout.findViewById(R.id.songsListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mIntent = new Intent(getActivity(),songPlayBackAcitivty.class);
                Bundle contentBundle = new Bundle();
                singleSongItem clickedItem = songsList.get(position);
                contentBundle.putParcelable("songItemToSend", songsList.get(position));
                contentBundle.putInt("songToSet", position);
                contentBundle.putParcelableArrayList("listOfSongs", songsList);
                Toast.makeText(getActivity(), songsList.get(position).getSongTitle(), Toast.LENGTH_SHORT).show();
                mIntent.putExtras(contentBundle);
                startActivity(mIntent);


            }
        });

//        if (savedInstanceState!=null)
//        {
//            mListView.onRestoreInstanceState(savedInstanceState.getParcelable("TheState"));
//
//        }

        return layout;

    }



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

            singleSongItem tempSongItem;
            do {
                tempSongItem = new singleSongItem();
                tempSongItem.setSongID(data.getLong(data.getColumnIndex("_ID")));
                tempSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
                tempSongItem.setSongArtistTile(data.getString(data.getColumnIndex("ARTIST")));
                tempSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
                //tempSongItem.setSongIconId(data.getString(data.getColumnIndex("album_art")));
                songsList.add(tempSongItem);
                tempSongItem = null;

            }
            while (data.moveToNext());
        }

        mSimpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mSimpleCursorAdapter.swapCursor(null);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
