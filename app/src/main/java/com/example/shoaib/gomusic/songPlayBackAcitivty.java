package com.example.shoaib.gomusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import java.util.ArrayList;

import adapters_Miscellenous.musicController;
import adapters_Miscellenous.musicService;
import adapters_Miscellenous.recyclerAdapter;


public class songPlayBackAcitivty extends AppCompatActivity implements MediaController.MediaPlayerControl, musicService.songPlayBackServiceCallBackInterface {

    private static final int Loader_ID_To_Query_Album_ForGivenSong = 91;
    private Toolbar mToolbar;
    private ImageView mImageView;
    private TextView mSongTitle;
    private TextView mSongAlbumTitle;
    private singleSongItem songNowPlaying;
    private ArrayList<singleSongItem> songsListForPlayBack;

    private Intent mPlayIntent;
    private static musicController mMusicController;
    private musicService mMusicService;
    private boolean mMusicServiceBound = false;
    private int received_Song_Index;
    private ServiceConnection mServiceConnection;

    private RecyclerView recylerView_ForSongs_FromSame_Album;
    private recyclerAdapter Adapter_For_RecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play_back_acitivty);

        mToolbar = (Toolbar) findViewById(R.id.mainapp_bar);
        //mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        CollapsingToolbarLayout mCollapse = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        mCollapse.setTitle("Song PlayBack");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recylerView_ForSongs_FromSame_Album = (RecyclerView) findViewById(R.id.mRecylerInsideSongPlayback);
        recylerView_ForSongs_FromSame_Album.setLayoutManager(new LinearLayoutManager(this));


        // mToolbar.setAlpha(0.6f);
        //mToolbar.setBackgroundColor(Color.TRANSPARENT);

        //songsListForPlayBack = new ArrayList<singleSongItem>();

        Intent mIntent = getIntent();
        Bundle b = mIntent.getExtras();

        if (b.getString("Caller", "cantSay").equals("songsFragment")) {
            songNowPlaying = b.getParcelable("songItemToSend");
            received_Song_Index = b.getInt("songToSet");
            songsListForPlayBack = b.getParcelableArrayList("listOfSongs");
            mImageView = (ImageView) findViewById(R.id.albumImageForSongActivity);

            Adapter_For_RecyclerView = new recyclerAdapter(this, songsListForPlayBack, false);
            recylerView_ForSongs_FromSame_Album.setAdapter(Adapter_For_RecyclerView);
            // recylerView_ForSongs_FromSame_Album.setMinimumHeight(0);
            // recylerView_ForSongs_FromSame_Album.setNestedScrollingEnabled(false);
            recylerView_ForSongs_FromSame_Album.setHasFixedSize(false);
        } else if (b.getString("Caller", "cantSay").equals("detailed_Activity")) {
            songNowPlaying = b.getParcelable("songItemToSend");
            received_Song_Index = b.getInt("songToSet");
            songsListForPlayBack = b.getParcelableArrayList("listOfSongs");
            mImageView = (ImageView) findViewById(R.id.albumImageForSongActivity);
//            mSongTitle = (TextView) findViewById(R.id.songNameForSongActivity);
//            mSongAlbumTitle = (TextView) findViewById(R.id.artistNameForSongActivity);
//            mSongTitle.setText(songNowPlaying.getSongTitle());
//            mSongAlbumTitle.setText(songNowPlaying.getSongArtistTile());
            Adapter_For_RecyclerView = new recyclerAdapter(this, songsListForPlayBack, false);
            recylerView_ForSongs_FromSame_Album.setAdapter(Adapter_For_RecyclerView);
            // recylerView_ForSongs_FromSame_Album.setMinimumHeight(0);
            // recylerView_ForSongs_FromSame_Album.setNestedScrollingEnabled(false);
            recylerView_ForSongs_FromSame_Album.setHasFixedSize(false);


        }
        mCollapse.setTitle(songNowPlaying.getSongTitle());

        if (songNowPlaying.getSongIconId() != null) {
            // Drawable drw = Drawable.createFromPath(songNowPlaying.getSongIconId());
//            ColorMatrix matrix = new ColorMatrix();
//            matrix.setSaturation(0); //0 means grayscale
//            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
//            drw.setColorFilter(cf);

            Bitmap bitmap = BitmapFactory.decodeFile(songNowPlaying.getSongIconId());
            mImageView.setImageBitmap(bitmap);

        }

        SetUpMusicPlayer();

        mServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                musicService.musicBinder mMusicBinder = (musicService.musicBinder) iBinder;
                mMusicService = mMusicBinder.getServiceInstanceForClient();
                mMusicService.setmSongsList(songsListForPlayBack);
                mMusicService.setSong(received_Song_Index);
                mMusicService.registerInterfaceForsongPlayBackActivity(songPlayBackAcitivty.this); //registering corresponding interface
                mMusicServiceBound = true;

                Log.e("HAHAH", "newConnectionBoss");

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mMusicServiceBound = false;

            }
        };

        if (mPlayIntent == null) {

            mPlayIntent = new Intent(this, musicService.class);
            bindService(mPlayIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
            startService(mPlayIntent);


        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putString("currentSongID",String.valueOf(songNowPlaying.getSongID()));
        //outState.putInt("currentPosition",mMusicService.getCurrentPostion());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(mServiceConnection);
        Log.e("New Service", "A new serivce");
    }


//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//
//        try{
//
//         // mMusicController.show(0);
//         //mMusicController.setBackgroundColor(Color.GREEN);
//
//
//
//        }catch(Exception e){
//           e.printStackTrace();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();

        //Service not implemented here, since onStart is called on various events, we dont want
        //service want to be established again and again.


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_play_back_acitivty, menu);
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
    public void start() {

        if (mMusicService != null && mMusicServiceBound) {

            mMusicService.go();
        }
    }

    @Override
    public void pause() {

        if (mMusicService != null && mMusicServiceBound) {

            mMusicService.pausePlayer();
        }

    }

    @Override
    public int getDuration() {

        if (mMusicService != null && mMusicServiceBound) {

            return mMusicService.getDuration();
        } else {

            return 0;
        }
    }

    @Override
    public int getCurrentPosition() {

        if (mMusicService != null && mMusicServiceBound) {

            return mMusicService.getCurrentPostion();
        } else {

            return 0;
        }

    }

    @Override
    public void seekTo(int i) {

        if (mMusicService != null && mMusicServiceBound) {

            mMusicService.seekTo(i);
        }
    }

    @Override
    public boolean isPlaying() {

        if (mMusicService != null && mMusicServiceBound) {

            return mMusicService.isSongPlaying();
        }

        return false;
    }

    @Override
    public int getBufferPercentage() {

        if (mMusicService != null && mMusicServiceBound) {

            return mMusicService.getBufferAge();
        }

        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }


    private void SetUpMusicPlayer() {

        mMusicController = new musicController(this);
        mMusicController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMusicService.nextSong();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMusicService.previousSong();

            }
        });

        mMusicController.setMediaPlayer(this);
        mMusicController.setAnchorView(findViewById(R.id.testFrame));
        mMusicController.setEnabled(true);


    }

    public static void displayMusicController() {

        mMusicController.show(0);
        mMusicController.setBackgroundColor(Color.argb(180, 57, 73, 171));
        //mMusicController.canScrollVertically(1);

    }


    @Override
    public void update_SongName_Text_Plus_ArtistText(singleSongItem anySong) {

        //  ((TextView)findViewById(R.id.songNameForSongActivity)).setText(anySong.getSongTitle());
        //  ((TextView)findViewById(R.id.artistNameForSongActivity)).setText(anySong.getSongArtistTile());
    }
}
