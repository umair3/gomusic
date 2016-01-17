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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapters_Miscellenous.DividerItemDecoration;
import adapters_Miscellenous.recyclerAdapter;


public class songsFragment_withRecycler extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int Loader1_ID_Cursor1 =33;
    private static final int Loader2_ID_Cursor2 =34;

    private static final Uri songsUri_External_Content_Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final Uri songsUri_Internal_Content_Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

    private static boolean Cursor1_ForLoader1_Loaded = false;
    private static boolean Cursor2_ForLoader2_Loaded = false;

    private Cursor External_Content_Cursor = null;
    private Cursor Internal_Content_Cursor = null;

    private ArrayList<singleSongItem> songsArrayList_For_ExternalContent_Loader1 = new ArrayList<singleSongItem>();
    private ArrayList<singleSongItem> songsArrayList_For_InternalContent_Loader2 = new ArrayList<singleSongItem>();

    private ArrayList<singleSongItem> songsArrayList_Final_BothContents;
    RecyclerView mSongsRecyclerList;
    recyclerAdapter mSongsListAdapter;
    private Intent mIntent;


    public songsFragment_withRecycler() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songsArrayList_Final_BothContents= new ArrayList<singleSongItem>();
        LoaderManager mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(Loader1_ID_Cursor1,null,this);
        mLoaderManager.initLoader(Loader2_ID_Cursor2,null,this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.songs_fragment_with_recycler, container, false);
        mSongsRecyclerList = (RecyclerView) layout.findViewById(R.id.songsRecyclerList);
       // fastScroller= (VerticalRecyclerViewFastScroller) layout.findViewById(R.id.fast_scroller);
       // mSongsListAdapter = new recyclerAdapter(getActivity(),songsList);
       // mSongsRecyclerList.setAdapter(mSongsListAdapter);
        mSongsRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mSongsRecyclerList.addItemDecoration(itemDecoration);

        //Assigning a scrollbar to our recyclerView
       // assignFastScrollerToRecyclerView ();

        return layout;
    }

//
//    private void assignFastScrollerToRecyclerView()
//    {
//
//
//        // Connect the recycler to the scroller (to let the scroller scroll the list)
//        fastScroller.setRecyclerView(mSongsRecyclerList);
//
//        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
//        mSongsRecyclerList.setOnScrollListener(fastScroller.getOnScrollListener());
//
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (id == Loader1_ID_Cursor1)
        {
            return new CursorLoader(getActivity(),songsUri_External_Content_Uri,new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION},null,null,MediaStore.Audio.Media.TITLE+" ASC");
        }
        else if (id == Loader2_ID_Cursor2)
        {
            return new CursorLoader(getActivity(),songsUri_Internal_Content_Uri,new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION},null,null,MediaStore.Audio.Media.TITLE+" ASC");
        }
        else
        {
            System.out.println("SongsFragment_withRecycler ---> id does not match either of the loaders in onCreateLoader ");
            Log.e("Might be Loader Issue","SongsFragment_withRecycler ---> id does not match either of the loaders in onCreateLoader ");
            return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        int loader_ID = loader.getId();


        if (loader_ID == Loader1_ID_Cursor1)
        {
            songsArrayList_For_ExternalContent_Loader1.clear();

            if (data.moveToFirst()) {

                singleSongItem tempSongItem;
                do {
                    tempSongItem = new singleSongItem();
                    tempSongItem.setSongSource("external");
                    tempSongItem.setSongID(data.getLong(data.getColumnIndex("_ID")));
                    tempSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
                    tempSongItem.setSongArtistTile(data.getString(data.getColumnIndex("ARTIST")));
                    tempSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
                    songsArrayList_For_ExternalContent_Loader1.add(tempSongItem);
                    tempSongItem = null;

                }
                while (data.moveToNext());
            }


        }
        else if (loader_ID == Loader2_ID_Cursor2)
        {
            songsArrayList_For_InternalContent_Loader2.clear();

            if (data.moveToFirst()) {

                singleSongItem tempSongItem;
                do {
                    tempSongItem = new singleSongItem();
                    tempSongItem.setSongSource("internal");
                    tempSongItem.setSongID(data.getLong(data.getColumnIndex("_ID")));
                    tempSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
                    tempSongItem.setSongArtistTile(data.getString(data.getColumnIndex("ARTIST")));
                    tempSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
                    songsArrayList_For_InternalContent_Loader2.add(tempSongItem);
                    tempSongItem = null;

                }
                while (data.moveToNext());
            }

        }
        else
        {
            System.out.println("SongsFragment_withRecycler ---> id does not match either of the loaders in OnLoadFinisted");
            Log.e("Might be Loader Issue","SongsFragment_withRecycler ---> id does not match either of the loaders in OnLoadFinisted");
        }


//        if (data.moveToFirst()) {
//
//            singleSongItem tempSongItem;
//            do {
//                tempSongItem = new singleSongItem();
//                tempSongItem.setSongID(data.getLong(data.getColumnIndex("_ID")));
//                tempSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
//                tempSongItem.setSongArtistTile(data.getString(data.getColumnIndex("ARTIST")));
//                tempSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
//                songsArrayList_Final_BothContents.add(tempSongItem);
//                tempSongItem = null;
//
//            }
//            while (data.moveToNext());
//        }

        songsArrayList_Final_BothContents.clear();
        songsArrayList_Final_BothContents.addAll(songsArrayList_For_ExternalContent_Loader1);
        songsArrayList_Final_BothContents.addAll(songsArrayList_Final_BothContents.size(),songsArrayList_For_InternalContent_Loader2);


        mSongsListAdapter = new recyclerAdapter(getActivity(),songsArrayList_Final_BothContents,true);
        mSongsRecyclerList.setAdapter(mSongsListAdapter);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
