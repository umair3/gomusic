package com.example.shoaib.gomusic;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters_Miscellenous.customSimpleCursorAdapter2Artist;


/**
* A simple {@link Fragment} subclass.
*/
public class artistsFragment extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private List<singleArtistItem> mArtistsItemsList;
    private Uri artistsUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    private String projection[] = new String[]{MediaStore.Audio.Artists._ID,MediaStore.Audio.ArtistColumns.ARTIST,
                                               MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS};

    private ListView mListView;
    private customSimpleCursorAdapter2Artist mListAdapter;
    private Intent mIntent;


    public artistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_artists, container, false);
        mListView = (ListView) layout.findViewById(R.id.artistListView);
        getLoaderManager().initLoader(25, null, this);
        mArtistsItemsList = new ArrayList<singleArtistItem>();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mIntent = new Intent(getActivity(),detailedArtistActivity.class);
                mIntent.putExtra("whoCalls","fromArtistFragment");
                mIntent.putExtra("theArtistName",mArtistsItemsList.get(position).getArtistItem_Title());
                startActivity(mIntent);

            }
        });


        return layout;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new android.support.v4.content.CursorLoader(getActivity(),artistsUri,projection,null,null,MediaStore.Audio.ArtistColumns.ARTIST+" ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mListAdapter = new customSimpleCursorAdapter2Artist(getActivity(),R.layout.explist_parentview,data,
                new String[]{"artist","number_of_tracks"},
                new int[]{R.id.ParentViewText,R.id.ParentViewSongsFromArtistText},0);
        mListView.setAdapter(mListAdapter);

        if (data.moveToFirst()){

            do {
                singleArtistItem mArtistItem = new singleArtistItem();
                mArtistItem.setArtistItem_Title(data.getString(data.getColumnIndex("artist")));
                mArtistItem.setArtistItem_TotalSongs(data.getInt(data.getColumnIndex("number_of_tracks")));
                mArtistsItemsList.add(mArtistItem);
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
