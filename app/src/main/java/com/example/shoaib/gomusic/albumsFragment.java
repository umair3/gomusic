package com.example.shoaib.gomusic;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters_Miscellenous.customImagarySimpleCursorAdapter;

//import adapters_Miscellenous.albumsExpandableListAdapter;
//import adapters_Miscellenous.albumsListRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class albumsFragment extends android.support.v4.app.Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private ListView albumsListView;
    Uri albumsUri;
    private customImagarySimpleCursorAdapter mSimpleCursorAdapter;
    private List<singleAlbumItem> albumsList;


    public albumsFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        albumsUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        getLoaderManager().initLoader(0, null, this);
        albumsList = new ArrayList<singleAlbumItem>();

        Log.v("The1Msg","simpleOnCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_albums, container, false);
        albumsListView = (ListView) layout.findViewById(R.id.albumListView);
        albumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                singleAlbumItem testItem= albumsList.get(position);
                Toast.makeText(getActivity(), testItem.getAlbumTitle(), Toast.LENGTH_SHORT).show();
                //String albumTitlePlusArt[] = new String [] {testItem.getAlbumTitle(),testItem.getBitmapPath()};
                Bundle albumItemBundle = new Bundle();
                albumItemBundle.putParcelable("theAlbumToSend",testItem);
                Intent albumIntent = new Intent(getActivity(),detailedAlbumActivity.class);
                albumIntent.putExtras(albumItemBundle);
                startActivity(albumIntent);

            }
        });
//        albumsUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//        getLoaderManager().initLoader(0, null, this);
//        albumsList = new ArrayList<singleAlbumItem>();

//        albumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                singleAlbumItem testItem= albumsList.get(position);
//                Toast.makeText(getActivity(), testItem.getAlbumTitle(), Toast.LENGTH_SHORT).show();
//                //String albumTitlePlusArt[] = new String [] {testItem.getAlbumTitle(),testItem.getBitmapPath()};
//                Bundle albumItemBundle = new Bundle();
//                albumItemBundle.putParcelable("theAlbumToSend",testItem);
//                Intent albumIntent = new Intent(getActivity(),detailedAlbumActivity.class);
//                albumIntent.putExtras(albumItemBundle);
//                startActivity(albumIntent);
//
//            }
//        });
        Log.v("The2Msg","OnCreateView called");
        return layout;
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //previous entry at the place of NUMBER OF SONGS Projection
        //MediaStore.Audio.Artists.Albums.NUMBER_OF_SONGS
        // MediaStore.Audio.Albums.ALBUM_ART,
        return new android.support.v4.content.CursorLoader(getActivity(), albumsUri,
               new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST,
                            MediaStore.Audio.Albums.NUMBER_OF_SONGS,MediaStore.Audio.AlbumColumns.ALBUM_ART},
                            null, null, MediaStore.Audio.Media.ALBUM + " ASC");
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {


        mSimpleCursorAdapter = new customImagarySimpleCursorAdapter(getActivity(),R.layout.single_album_item,data,
                               new String[]{"ALBUM","Artist","numsongs","album_art"},new int[]{R.id.albumTitleText,R.id.albumSingerText,R.id.albumTotalSongsText,R.id.albumCoverPhoto},0);
        albumsListView.setAdapter(mSimpleCursorAdapter);

        if (data.moveToFirst()){

            do {
                singleAlbumItem mAlbumItem = new singleAlbumItem();
                mAlbumItem.setAlbumID(data.getInt(data.getColumnIndex("_ID")));
                mAlbumItem.setAlbumSingerName(data.getString(data.getColumnIndex("Artist")));
                mAlbumItem.setAlbumTitle(data.getString(data.getColumnIndex("ALBUM")));
                mAlbumItem.setAlbumTotalSongs(data.getInt(data.getColumnIndex("numsongs")));
                mAlbumItem.setBitmapPath(data.getString(data.getColumnIndex("album_art")));
                albumsList.add(mAlbumItem);
            }
            while(data.moveToNext());



        }

        mSimpleCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

        mSimpleCursorAdapter.swapCursor(null);
    }


//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//
//        if (cursor != null && cursor.moveToFirst()) {
//
//            int albumIDColumn = cursor.getColumnIndex
//                    (MediaStore.Audio.Albums._ID);
//
//            int albumNameColumn = cursor.getColumnIndex
//                    (MediaStore.Audio.Albums.ALBUM);
//
//            int albumArtColumn = cursor.getColumnIndex
//                    (MediaStore.Audio.Albums.ALBUM_ART);
//
//            int albumTotalSongsColumn = cursor.getColumnIndex
//                    (MediaStore.Audio.Albums.NUMBER_OF_SONGS);
//
//            do {
//
//                int currentAlbumId = cursor.getInt(albumIDColumn);
//                int currentAlbumArt = cursor.getInt(albumArtColumn);
//                int currentAlbumTotalSongs = cursor.getInt(albumTotalSongsColumn);
//                String currentAlbumName = cursor.getString(albumNameColumn);
//
//                final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
//                Uri albumArtUri = ContentUris.withAppendedId(ART_CONTENT_URI, currentAlbumId);
//
//                Bitmap image = null;
//                try {
//                    image = MediaStore.Images.Media.getBitmap(contentResolver, albumArtUri);
//                } catch (Exception exception) {
//                    // log error
//                }
//
//                albumsList.add(new singleAlbumItem(currentAlbumId, currentAlbumArt,
//                        currentAlbumTotalSongs, currentAlbumName, image));
//
//            }
//            while (cursor.moveToNext());
//
//        }
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }


//    public void getDataAlbumCase() {
//
//        Uri albumsUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//        Cursor albumsCursor = contentResolver.query(albumsUri, new String[]{MediaStore.Audio.Albums._ID,
//                MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART,
//                MediaStore.Audio.Albums.NUMBER_OF_SONGS}, null, null, MediaStore.Audio.Media.ALBUM + " ASC");
//
//        if (albumsCursor != null && albumsCursor.moveToFirst()) {
//
//            int albumIDColumn = albumsCursor.getColumnIndex
//                    (MediaStore.Audio.Albums._ID);
//
//            int albumNameColumn = albumsCursor.getColumnIndex
//                    (MediaStore.Audio.Albums.ALBUM);
//
//            int albumArtColumn = albumsCursor.getColumnIndex
//                    (MediaStore.Audio.Albums.ALBUM_ART);
//
//            int albumTotalSongsColumn = albumsCursor.getColumnIndex
//                    (MediaStore.Audio.Albums.NUMBER_OF_SONGS);
//
//            do {
//
//                int currentAlbumId = albumsCursor.getInt(albumIDColumn);
//                int currentAlbumArt = albumsCursor.getInt(albumArtColumn);
//                int currentAlbumTotalSongs = albumsCursor.getInt(albumTotalSongsColumn);
//                String currentAlbumName = albumsCursor.getString(albumNameColumn);
//
//                final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
//                Uri albumArtUri = ContentUris.withAppendedId(ART_CONTENT_URI, currentAlbumId);
//
//                Bitmap image = null;
//                try {
//                    image = MediaStore.Images.Media.getBitmap(contentResolver, albumArtUri);
//                } catch (Exception exception) {
//                    // log error
//                }
//
//                albumsList.add(new singleAlbumItem(currentAlbumId, currentAlbumArt,
//                        currentAlbumTotalSongs, currentAlbumName, image));
//
//            }
//            while (albumsCursor.moveToNext());
//
//        }
//
//
//    }


        //Activity bracket
    }


//    public void getSongsForEveryAlbum(String theAlbum) {
//
//        String[] columnsToExtract = {MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.TITLE};
//
//        String where = MediaStore.Audio.Media.ALBUM + "=?";
//
//        String whereVal[] = {theAlbum};
//
//        String orderBy = android.provider.MediaStore.Audio.Media.TITLE;
//
//       Cursor cursor = contentResolver.query(
//               MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columnsToExtract,
//               where, whereVal, orderBy);
//
//
//        if (cursor != null && cursor.moveToFirst()){
//
//            int idColumn = cursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media._ID);
//            int titleColumn =cursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media.TITLE);
//            int artistColumn =cursor.getColumnIndex
//                    (android.provider.MediaStore.Audio.Media.ARTIST);
//
//
//            do {
//                long thisId = cursor.getLong(idColumn);
//                String thisTitle = cursor.getString(titleColumn);
//                String thisArtist = cursor.getString(artistColumn);
//                songsList.add(new singleSongItem(thisId, thisTitle, thisArtist));
//            }
//            while (cursor.moveToNext());
//        }
//
//
//
//    }


