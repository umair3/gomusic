package com.example.shoaib.gomusic;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import adapters_Miscellenous.DividerItemDecoration;


/**
* A simple {@link Fragment} subclass.
*/
public class artistsFragment extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<singleArtistItem> mArtistsItemsList;
    private Uri artistsUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    private String projection[] = new String[]{MediaStore.Audio.Artists._ID,MediaStore.Audio.ArtistColumns.ARTIST,
                                               MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS};

//    private ListView mListView;
//    private customSimpleCursorAdapter2Artist mListAdapter;
//    private Intent mIntent;

    private RecyclerView mArtistRecyclerView;
    private artistRecyclerAdapter mArtistRecyclerViewAdapter;


    public artistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_artists, container, false);
        mArtistRecyclerView = (RecyclerView) layout.findViewById(R.id.artistRecyclerList);
        mArtistsItemsList = new ArrayList<singleArtistItem>();
        getLoaderManager().initLoader(25, null, this);

        //It worked with Listview now we have switched to REcyclerView and TouchHandling is implemented in the recyclerView adapter.
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                mIntent = new Intent(getActivity(),detailedArtistActivity.class);
//                mIntent.putExtra("whoCalls","fromArtistFragment");
//                mIntent.putExtra("theArtistName",mArtistsItemsList.get(position).getArtistItem_Title());
//                startActivity(mIntent);
//
//            }
//        });


        return layout;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new android.support.v4.content.CursorLoader(getActivity(),artistsUri,projection,null,null,MediaStore.Audio.ArtistColumns.ARTIST+" ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        // We dont need this part of code now, since here an adapter for listVIew is being Initialized,
        // for developing a listView, whereAs now we have implemented our Own reyclerView Adapater
        // it has its own implementation, and its class (Extended) is defined below in this class

//        mListAdapter = new customSimpleCursorAdapter2Artist(getActivity(),R.layout.explist_parentview,data,
//                new String[]{"artist","number_of_tracks"},
//                new int[]{R.id.ParentViewText,R.id.ParentViewSongsFromArtistText},0);
//        mListView.setAdapter(mListAdapter);



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

        mArtistRecyclerViewAdapter = new artistRecyclerAdapter(getActivity(),mArtistsItemsList);
        mArtistRecyclerView.setAdapter(mArtistRecyclerViewAdapter);
        mArtistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mArtistRecyclerView.addItemDecoration(itemDecoration);


        // mListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        //mListAdapter.swapCursor(null);

    }


    public class artistRecyclerAdapter extends RecyclerView.Adapter<artistRecyclerAdapter.artistRowViewHolder>
    {

        private ArrayList<singleArtistItem> artistList;
        private LayoutInflater layoutInflater;
        private Context context;


        public artistRecyclerAdapter (ArrayList<singleArtistItem> theArtistList)
        {
            this.artistList = theArtistList;

        }
        public artistRecyclerAdapter(Context context, ArrayList<singleArtistItem> theArtistList){

            this.context = context;
            this.artistList = theArtistList;
            layoutInflater = LayoutInflater.from(context);



        }
        public class artistRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            // These are for ViewHolder
            TextView artistName;
            TextView songsCount;

            private Context theContext;
            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public artistRowViewHolder(View itemView,Context mContext) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                theContext = mContext;
                artistName = (TextView) itemView.findViewById(R.id.ParentViewText);
                songsCount = (TextView) itemView.findViewById(R.id.ParentViewSongsFromArtistText);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
//                final Intent mIntent = new Intent(context,songPlayBackAcitivty.class);
//                Bundle contentBundle = new Bundle();
//                singleSongItem clickedItem = artistList.get(getAdapterPosition());
//                contentBundle.putParcelable("songItemToSend", artistList.get(getAdapterPosition()));
//                contentBundle.putInt("songToSet", getAdapterPosition());
//                contentBundle.putParcelableArrayList("listOfSongs", songsList);
//               // Snackbar.make(itemView,songsList.get(getAdapterPosition()).getSongTitle(), Snackbar.LENGTH_SHORT).show();
//                //Toast.makeText(context, songsList.get(getPosition()).getSongTitle(), Toast.LENGTH_SHORT).show();
//                mIntent.putExtras(contentBundle);
                final Intent theIntent = new Intent(getActivity(),detailedArtistActivity.class);
                theIntent.putExtra("whoCalls","fromArtistFragment");
                theIntent.putExtra("theArtistName",artistList.get(getAdapterPosition()).getArtistItem_Title());
                //startActivity(theIntent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        context.startActivity(theIntent);
                    }
                }, 1000);
                // context.startActivity(mIntent);
            }
        }
        @Override
        public artistRecyclerAdapter.artistRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View oneRowView = layoutInflater.inflate(R.layout.explist_parentview, parent, false);

            // Return a new holder instance
            artistRowViewHolder mViewHolder = new artistRowViewHolder(oneRowView,context);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(artistRecyclerAdapter.artistRowViewHolder holder, int position) {

            singleArtistItem mCurrentArtistItem = artistList.get(position);

            (holder.artistName).setText(mCurrentArtistItem.getArtistItem_Title());
            (holder.songsCount).setText(String.valueOf(mCurrentArtistItem.getArtistItem_TotalSongs()));


        }

        @Override
        public int getItemCount() {
            return artistList.size();
        }
    }
}


