package com.example.shoaib.gomusic;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import adapters_Miscellenous.BitmapWorkerTask;
import adapters_Miscellenous.DividerItemDecoration;
import adapters_Miscellenous.albumFilterClass;

//import adapters_Miscellenous.albumsExpandableListAdapter;
//import adapters_Miscellenous.albumsListRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class albumsFragment extends android.support.v4.app.Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView albumsRecylerView;
    Uri albumsUri;
    public albumRecyclerAdapter mAlbumsRecylerAdapter;
    private ArrayList<singleAlbumItem> albumsList;


    public albumsFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        albumsUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        getLoaderManager().initLoader(0, null, this);
        albumsList = new ArrayList<singleAlbumItem>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_albums, container, false);
        albumsRecylerView = (RecyclerView) layout.findViewById(R.id.albumsRecyclerList);
        albumsRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        albumsRecylerView.addItemDecoration(itemDecoration);

        return layout;
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //previous entry at the place of NUMBER OF SONGS Projection
        //MediaStore.Audio.Artists.Albums.NUMBER_OF_SONGS
        // MediaStore.Audio.Albums.ALBUM_ART,
        return new android.support.v4.content.CursorLoader(getActivity(), albumsUri,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST,
                        MediaStore.Audio.Albums.NUMBER_OF_SONGS, MediaStore.Audio.AlbumColumns.ALBUM_ART},
                null, null, MediaStore.Audio.Media.ALBUM + " ASC");
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {


//        mSimpleCursorAdapter = new customImagarySimpleCursorAdapter(getActivity(), R.layout.single_album_item, data,
//                new String[]{"ALBUM", "Artist", "numsongs", "album_art"}, new int[]{R.id.albumTitleText, R.id.albumSingerText, R.id.albumTotalSongsText, R.id.albumCoverPhoto}, 0);
//        albumsListView.setAdapter(mSimpleCursorAdapter);


        if (data.moveToFirst()) {

            do {
                singleAlbumItem mAlbumItem = new singleAlbumItem();
                mAlbumItem.setAlbumID(data.getInt(data.getColumnIndex("_ID")));
                mAlbumItem.setAlbumSingerName(data.getString(data.getColumnIndex("Artist")));
                mAlbumItem.setAlbumTitle(data.getString(data.getColumnIndex("ALBUM")));
                mAlbumItem.setAlbumTotalSongs(data.getInt(data.getColumnIndex("numsongs")));
                mAlbumItem.setBitmapPath(data.getString(data.getColumnIndex("album_art")));
                albumsList.add(mAlbumItem);
            }
            while (data.moveToNext());


        }
        mAlbumsRecylerAdapter = new albumRecyclerAdapter(getActivity(),albumsList);
        albumsRecylerView.setAdapter(mAlbumsRecylerAdapter);



        //mSimpleCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

       // mSimpleCursorAdapter.swapCursor(null);
    }




    public class albumRecyclerAdapter extends RecyclerView.Adapter<albumRecyclerAdapter.albumRowViewHolder> implements Filterable{

        public ArrayList<singleAlbumItem> theAlbumsList;
        private LayoutInflater layoutInflater;
        private Context context;
        public ArrayList<singleAlbumItem> UnchangedUnmodifiedSongsList;


        public albumRecyclerAdapter(ArrayList<singleAlbumItem> theList) {

            this.UnchangedUnmodifiedSongsList = new ArrayList<singleAlbumItem>();
            this.UnchangedUnmodifiedSongsList.addAll(theList);
            this.theAlbumsList = theList;

        }

        public albumRecyclerAdapter(Context context, ArrayList<singleAlbumItem> theList) {

            this.context = context;
            this.theAlbumsList = theList;
            this.UnchangedUnmodifiedSongsList = new ArrayList<singleAlbumItem>();
            this.UnchangedUnmodifiedSongsList.addAll(theList);
            layoutInflater = LayoutInflater.from(context);


        }


        public class albumRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            // These are for ViewHolder
            ImageView albumArt;
            TextView albumTitle;
            TextView albumArtist;
            TextView songsCount;
            private Context theContext;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public albumRowViewHolder(View itemView, Context mContext) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                theContext = mContext;
                albumArt = (ImageView) itemView.findViewById(R.id.albumCoverPhoto) ;
                albumTitle = (TextView) itemView.findViewById(R.id.albumTitleText);
                albumArtist = (TextView) itemView.findViewById(R.id.albumSingerText);
                songsCount = (TextView) itemView.findViewById(R.id.albumTotalSongsText);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                singleAlbumItem testItem = albumsList.get(getAdapterPosition());
                Bundle albumItemBundle = new Bundle();
                albumItemBundle.putParcelable("theAlbumToSend", testItem);
                final Intent theIntent = new Intent(getActivity(), detailedAlbumActivity.class);
                theIntent.putExtras(albumItemBundle);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        context.startActivity(theIntent);
                    }
                }, 500);



            }
        }

        //Just a helper method that creates and runs the task to load albumArt
        public void loadBitmap (String bitmapPath, ImageView mImage)
        {
            BitmapWorkerTask mTask = new BitmapWorkerTask(mImage);
            mTask.execute(bitmapPath);
        }

        @Override
        public albumRecyclerAdapter.albumRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View oneRowView = layoutInflater.inflate(R.layout.single_album_item, parent, false);
            // Return a new holder instance
            albumRowViewHolder mViewHolder = new albumRowViewHolder(oneRowView, context);
            return mViewHolder;
        }

        @Override
        public void onBindViewHolder(albumRecyclerAdapter.albumRowViewHolder holder, int position) {

            singleAlbumItem mCurrentAlbumItem = albumsList.get(position);


            Bitmap art;
            if (mCurrentAlbumItem.getBitmapPath()!=null)
            {
                //set the current album's art on the background thread. by an asynctask
                loadBitmap(mCurrentAlbumItem.getBitmapPath(),holder.albumArt);

//                art = BitmapFactory.decodeFile(mCurrentAlbumItem.getBitmapPath());
//                if (art != null) {
//
//                    holder.albumArt.setImageBitmap(art);
//                    holder.albumArt.setAlpha(1.0f);
//                    //art = getRoundedShape(art);
//                    //((ImageView)view).setImageBitmap(art);
//                }
//                else {
//
//
//                    holder.albumArt.setImageResource(R.drawable.ic_album_black_48dp);
//                    holder.albumArt.setAlpha(0.5f);
//                    //   ((ImageView)view).setImageBitmap(toDefArt);
//                    //   ((ImageView)view).setAlpha(57.0f);
//                }

            }
            else
            {
                holder.albumArt.setImageResource(R.drawable.ic_album_black_48dp);
                holder.albumArt.setAlpha(0.5f);

            }
            (holder.albumTitle).setText(mCurrentAlbumItem.getAlbumTitle());
            (holder.albumArtist).setText(String.valueOf(mCurrentAlbumItem.getAlbumSingerName()));
            (holder.songsCount).setText(String.valueOf(mCurrentAlbumItem.getAlbumTotalSongs()));


        }

        @Override
        public int getItemCount() {
            return theAlbumsList.size();
        }

        @Override
        public Filter getFilter() {
            return new albumFilterClass(albumRecyclerAdapter.this,albumsList);
        }
    }

}

