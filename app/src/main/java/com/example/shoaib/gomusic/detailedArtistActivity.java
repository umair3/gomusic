package com.example.shoaib.gomusic;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters_Miscellenous.customSimpleCursorAdapter;


public class detailedArtistActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar mToolbar;
    private customSimpleCursorAdapter mSimpleCursorAdapter;
    private ListView mListView;
    private ArrayList<singleSongItem> songsList;
    private String artistName;
    private int playListID;
    private String playListName;
    Intent mIntent;
    boolean ifArtist=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_artist);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setAlpha(0.9f);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);

        mListView = (ListView) findViewById(R.id.artistSongsListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mIntent = new Intent(detailedArtistActivity.this,songPlayBackAcitivty.class);
                Bundle songItemBundle = new Bundle();
                songItemBundle.putParcelable("songItemToSend",songsList.get(position));
                songItemBundle.putInt("songToSet", position);
                songItemBundle.putParcelableArrayList("listOfSongs", songsList);
                songItemBundle.putString("Caller", "detailed_Activity");
                Toast.makeText(getApplicationContext(), songsList.get(position).getSongTitle(), Toast.LENGTH_SHORT).show();
                mIntent.putExtras(songItemBundle);
                startActivity(mIntent);
            }
        });
        songsList = new ArrayList<singleSongItem>();
        Intent mIntent = getIntent();
        if (mIntent.getStringExtra("whoCalls").toString().equals("fromArtistFragment"))
        {
            ifArtist=true;
            artistName=mIntent.getStringExtra("theArtistName");
            getSupportActionBar().setTitle(artistName);
            Bundle b = new Bundle();
            b.putString("queryWhat","artist");
            getSupportLoaderManager().initLoader(14,b,this);
        }
        else
        {
            playListID=mIntent.getIntExtra("playListID",-1);
            playListName = mIntent.getStringExtra("playListName");
            if (playListID!=-1)
            {
                getSupportActionBar().setTitle(playListName);
                Bundle b = new Bundle();
                b.putString("queryWhat","playList");
                getSupportLoaderManager().initLoader(14,b,this);
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_artist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public android.support.v4.content.Loader<Cursor>  onCreateLoader(int i, Bundle bundle) {

        if (bundle.getString("queryWhat").toString().equals("artist"))
        {
            String where = MediaStore.Audio.Media.ARTIST
                    + "=?";
            //String toGet = Integer.toString(selectedAlbum_ID);
            String whereVal[] = new String[]{artistName};
            return new android.support.v4.content.CursorLoader(this,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.ARTIST},where,whereVal,
                    MediaStore.Audio.Media.TITLE+ " ASC");
        }
        else
        {

            //Here we have the playlist's ID and the content provider does not need a selectionClause and selectionArgs
            //for querying the songs for the given playlist, we have to Directly give the Uri for the specific playlist
            //and projection. ContentProvider automatically queries and provides us with the songs of that specific playlist.
            //Remember querying songs for a playlist is a little tricky and different from usual querying from content provider's
            // Uri+projection+selectionClause+selectionArgs concept a little bit.
            return new android.support.v4.content.CursorLoader(this,MediaStore.Audio.Playlists.Members.getContentUri("external",playListID),
                    new String[]{MediaStore.Audio.Playlists.Members._ID,MediaStore.Audio.Playlists.Members.AUDIO_ID,MediaStore.Audio.Playlists.Members.TITLE,MediaStore.Audio.Playlists.Members.DURATION,MediaStore.Audio.Playlists.Members.ARTIST},
                    null,null,MediaStore.Audio.Playlists.Members.TITLE+ " ASC");

        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor data) {

        mSimpleCursorAdapter = new customSimpleCursorAdapter(this,R.layout.single_songproper_item2
                ,data,
                new String []{"TITLE","ARTIST","DURATION"},
                new int []{R.id.songTitleProper2,R.id.songArtistProper2,R.id.songDuration2},0);

        mListView.setAdapter(mSimpleCursorAdapter);

        if (data.moveToFirst()){

            do {
                singleSongItem mSongItem = new singleSongItem();
                if (ifArtist)
                {
                    mSongItem.setSongID(data.getLong(data.getColumnIndex(MediaStore.Audio.Media._ID)));
                }
                else
                {
                    mSongItem.setSongID(data.getLong(data.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID)));
                }
                mSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
                mSongItem.setSongArtistTile(data.getString(data.getColumnIndex("Artist")));
                mSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
               // mSongItem.setSongIconId(data.getString(data.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART)));
                songsList.add(mSongItem);
            }
            while(data.moveToNext());



        }

//        Drawable drw = Drawable.createFromPath(BitmapPathNeeded);
//        setBack(drw);
        mSimpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

        mSimpleCursorAdapter.swapCursor(null);
    }
}
