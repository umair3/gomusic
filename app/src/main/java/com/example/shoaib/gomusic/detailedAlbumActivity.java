package com.example.shoaib.gomusic;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import adapters_Miscellenous.customSimpleCursorAdapter;


public class detailedAlbumActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>{

    private Toolbar mToolbar;
    private String selectedAlbum_Title;
    private String BitmapPathNeeded;
    private customSimpleCursorAdapter mSimpleCursorAdapter;
    private ListView mListView;
    private ArrayList<singleSongItem> songsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_album);


        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setAlpha(0.9f);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        mListView = (ListView) findViewById(R.id.albumSongslistView);

        Intent mIntent = getIntent();
        Bundle b= mIntent.getExtras();
        singleAlbumItem temptoAlbum = b.getParcelable("theAlbumToSend");

        //selectedAlbum_Title= mIntent.getStringExtra("the_Title");
       // String mTitlePlusArt =
        selectedAlbum_Title= temptoAlbum.getAlbumTitle();
        BitmapPathNeeded = temptoAlbum.getBitmapPath();

        if (BitmapPathNeeded!=null){

            Drawable drw = Drawable.createFromPath(BitmapPathNeeded);
//            ColorMatrix matrix = new ColorMatrix();
//            matrix.setSaturation(0); //0 means grayscale
//            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
//            drw.setColorFilter(cf);
            setBack(drw);


        }


        getSupportActionBar().setTitle(selectedAlbum_Title);
        getSupportLoaderManager().initLoader(0,null,this);
        //getSongsForAlbum(selectedAlbum_ID);
        songsList = new ArrayList<singleSongItem>();


       // mMusicService.passController(mMusicController);



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


               Intent mIntent = new Intent(detailedAlbumActivity.this,songPlayBackAcitivty.class);
               Bundle songItemBundle = new Bundle();
               songItemBundle.putParcelable("songItemToSend",songsList.get(position));
               songItemBundle.putInt("songToSet", position);
               songItemBundle.putParcelableArrayList("listOfSongs", songsList);
               Toast.makeText(getApplicationContext(),songsList.get(position).getSongTitle(),Toast.LENGTH_SHORT).show();
               mIntent.putExtras(songItemBundle);
               startActivity(mIntent);

            }
        });

      //  setUpMusicController();  // Activate The MusicController Bar




//        if (((Activity)this).isFinishing()) {
//           // mediaController.show(5000);
//            mMusicController.show(5000);
//        }
      // mMusicController.show(0);

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//
//        return super.onTouchEvent(event);
//
//
//    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void setBack (Drawable drw){

//        ImageView view = (ImageView) findViewById(R.id.backAlbumCover);
//        view.setBackground(drw);


        RelativeLayout view = (RelativeLayout) findViewById(R.id.rooRelative);
        view.setBackground(drw);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_album, menu);
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
        else if (id == R.id.home){

            NavUtils.navigateUpFromSameTask(this);

        }
//        else if (id == R.id.action_end){
//
//            stopService(mPlayIntent);
//            mMusicService=null;
//            System.exit(0);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String where = MediaStore.Audio.Media.ALBUM
                + "=?";
        //String toGet = Integer.toString(selectedAlbum_ID);
        String whereVal[] = new String[]{selectedAlbum_Title};
        return new android.support.v4.content.CursorLoader(this,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION},where,whereVal,
                MediaStore.Audio.Media.TITLE+ " ASC");
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {

        // manipulate query data
//        do {
//
//           int durationInt=  data.getInt(data.getColumnIndex("DURATION"));
//            durationInt = durationInt/(1000); //seconds
//            durationInt = durationInt/(60);
//            //data.
//
//        }
//        while(data.moveToNext());

//        View mView = (View) findViewById(R.id.mainDetailView);
//        TextView mText= (TextView)mView.findViewById(R.id.songTitleProper);
//        mText.setTextColor(Color.WHITE);
//        mText= (TextView)mView.findViewById(R.id.songArtistProper);
//        mText.setTextColor(Color.WHITE);
        //ImageView mImage= (ImageView)mView.findViewById(R.id.img);



        mSimpleCursorAdapter = new customSimpleCursorAdapter(this,R.layout.single_songproper_item2
                ,data,
                new String []{"TITLE","ARTIST","DURATION"},
                new int []{R.id.songTitleProper2,R.id.songArtistProper2,R.id.songDuration2},0);

        mListView.setAdapter(mSimpleCursorAdapter);

        if (data.moveToFirst()){

            do {
                singleSongItem mSongItem = new singleSongItem();
                mSongItem.setSongID(data.getLong(data.getColumnIndex("_ID")));
                mSongItem.setSongTitle(data.getString(data.getColumnIndex("TITLE")));
                mSongItem.setSongArtistTile(data.getString(data.getColumnIndex("Artist")));
                mSongItem.setSongDuration(data.getInt(data.getColumnIndex("DURATION")));
                mSongItem.setSongIconId(BitmapPathNeeded);
                //mSongItem.setSongIconId(data.getString(data.getColumnIndex("album_art")));
                //mAlbumItem.setAlbumTotalSongs(data.getInt(data.getColumnIndex("album_art")));
                songsList.add(mSongItem);
            }
            while(data.moveToNext());



        }

//        Drawable drw = Drawable.createFromPath(BitmapPathNeeded);
//        setBack(drw);
        mSimpleCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

        mSimpleCursorAdapter.swapCursor(null);

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Do Stuff When Activity is not being shown, or minimized

    }
//
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//
//        try{
//
//          mMusicController.show(0);
//
//
//        }catch(Exception e){
//           e.printStackTrace();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

      //  unbindService(mMusicServiceConnection);
      //  mMusicServiceBound=false;

    }

    // Implementation of MediaPlayer Controller

    //Helper Method to Setup Controller   ---- give this one A Look AfterWards


    }
