package adapters_Miscellenous;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import com.example.shoaib.gomusic.singleSongItem;
import com.example.shoaib.gomusic.songPlayBackAcitivty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoaib on 9/25/2015.
 */
public class musicService extends Service implements MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnBufferingUpdateListener {

    public interface MusicServiceCallBackInterface
    {
        void UpdateBottomInfromerDisplay(final singleSongItem anySong);
        void Set_MainActivity_BottomInfromer_SongData_ForFirstTime (final singleSongItem anySong);
        void Update_PlayPauseButton_State(boolean whatState);

    }

    //for updating the current song text and artist text being shown in songPlayBackActivity
    public interface songPlayBackServiceCallBackInterface
    {
        void update_SongName_Text_Plus_ArtistText (singleSongItem anySong);

    }

    private MusicServiceCallBackInterface mInterface=null;
    private songPlayBackServiceCallBackInterface mSongPlayBackInterface=null;

    private MediaPlayer mMediaPlayer;

    private int songPosition=0;
    private List<singleSongItem> mSongsList;
    private final musicBinder mMusicBinder = new musicBinder();


    private SharedPreferences mSharedFile;
    private SharedPreferences.Editor mEditor;
    private static String STRING_SONG_PLAYEDPREVIOUSLY_ID_STORAGE = "theSongsIDtoL";
    private static String STRING_SONG_PLAYEDPREVIOUSLY_NAME_STORAGE = "theSongsNAMEtoL";
    private static String STRING_SONG_PLAYEDPREVIOUSLY_CURRENTPOS_STORAGE = "theSongsCURRENTPOStoL";
    private static String STRING_SONG_PLAYEDPREVIOUSLY_SONGARTIST_STORAGE = "theSongsARTISTtoL";

    // Here Starts the implementation of the Service itself

    @Override
    public void onCreate() {
        super.onCreate();

        mSharedFile = getSharedPreferences(SoundCloud_Class.prefsFile_Name,MODE_PRIVATE);
        mEditor = mSharedFile.edit();
        songPosition =0;
        mMediaPlayer= new MediaPlayer();
        initmMediaPlayer();

        //Retrieve the song that was being played previously before closing the player
        //Retiieve it and update the bottominformer of main Activity
       // RetriveLastSavedSongData();


    }

    public class musicBinder extends Binder {

        public musicService getServiceInstanceForClient(){

            return musicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        //Makes sure, everytime Interfaces are registered with the current
        //instance of the corresponding activity
        mInterface=null;
        mSongPlayBackInterface=null;

        return mMusicBinder;


    }

    @Override
    public boolean onUnbind(Intent intent) {

//       mMediaPlayer.stop();
//       mMediaPlayer.release();

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Managing BottomInformer Layout and maintaining the saving and retrieving of the song
        //that user was Playing last time before exiting

        //COmmented at the moment but at last it has to be called here
        //abhi jugar lgai he, yahan hi Last song ka data save hona chahiye
        //abhi service ka on destroy call nahin horha
       // saveSongDataForRetrievel();
        mMediaPlayer.stop();
        mMediaPlayer.release();
        stopSelf();
    }

    public void resetPlayer (){

        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    // Here ends the Implementation of service itself



    // Here Come The Methods to Setup MediaPlayer And PlaySongs

    public void initmMediaPlayer(){

        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);

    }

    public void setmSongsList (ArrayList<singleSongItem> theSongsList){

        mSongsList = theSongsList;
    }

    public void setSong(int songIndex){

        songPosition=songIndex;
        playSong();
    }

    public void nextSong(){

        if(songPosition < mSongsList.size()-1){

            songPosition++;
            playSong();
        }
        else{

            songPosition=0;
            playSong();
        }
    }

    public void previousSong(){

        if (songPosition > 0){

            songPosition--;
            playSong();
        }
        else{

            songPosition=mSongsList.size()-1;
            playSong();
        }
    }

    public int getDuration (){

        return mMediaPlayer.getDuration();
    }

    public int getCurrentPostion (){

        return mMediaPlayer.getCurrentPosition();
    }

    public int getSessionId (){

        return mMediaPlayer.getAudioSessionId();
    }

    public int getBufferAge(){

        int percentage = (mMediaPlayer.getCurrentPosition() * 100) / mMediaPlayer.getDuration();
        return percentage;
    }


    public boolean isSongPlaying(){

        return mMediaPlayer.isPlaying();
    }



    public void pausePlayer (){

        mInterface.Update_PlayPauseButton_State(false);
        if (mMediaPlayer.isPlaying())
        {
            mMediaPlayer.pause();
        }
    }

    public void seekTo (int pos){

        mMediaPlayer.seekTo(pos);
    }

    public void go (){

//        if(songPosition==0 && !mMediaPlayer.isPlaying()){
//
//
//            playSong();
//
//        }
//        else{
//
//            mMediaPlayer.start();
//
//        }
                                // Made a change here , Commented the if else and added this simple
                                // simple line of code
        mMediaPlayer.start();

    }

    public void resumePlayer()
    {
        mInterface.Update_PlayPauseButton_State(true);
        if (!mMediaPlayer.isPlaying())
        {
            mMediaPlayer.start();
        }
    }

    public void playSong (){
        mMediaPlayer.reset();
        singleSongItem tempSong = mSongsList.get(songPosition);

        saveSongDataForRetrievel();
        mInterface.Update_PlayPauseButton_State(true);
        if (mSongPlayBackInterface!=null)
        {
            mSongPlayBackInterface.update_SongName_Text_Plus_ArtistText(mSongsList.get(songPosition));
        }
        //Updating the bottom informer in the main activity or For AnyActivity
        if (mInterface!=null)
        {
            mInterface.UpdateBottomInfromerDisplay(tempSong);
        }

        Uri trackUri;

      //  if (tempSong.getSongSource()=="internal")
      //  {
      //      trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,tempSong.getSongID());
      //  }
      //  else
      //  {
            trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,tempSong.getSongID());
      //  }


        try{

            mMediaPlayer.setDataSource(getApplicationContext(),trackUri);

        }
        catch (Exception e){

            Log.e("MUSIC SERVICE", "Error setting data source", e);
            trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,tempSong.getSongID());
            try
            {
                mMediaPlayer.setDataSource(getApplicationContext(),trackUri);
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }

        }


            mMediaPlayer.prepareAsync();



    }

    // Here End the Methods to Setup MediaPlayer And PlaySongs



    // Here Are The Implementations of the Required Interfaces
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        nextSong();

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {



    }

    public boolean getMediaPlayerState ()
    {

        if (mMediaPlayer.isPlaying())
        {
            return true;
        }
        return false;
    }
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        mediaPlayer.start();
        songPlayBackAcitivty.displayMusicController();


    }


    //Managing BottomInformer Layout and maintaining the saving and retrieving of the song
    //that user was Playing last time before exiting
    public void saveSongDataForRetrievel()
    {
        String songName =mSongsList.get(songPosition).getSongTitle();
        String songArtist = mSongsList.get(songPosition).getSongArtistTile();

        mEditor.putString(STRING_SONG_PLAYEDPREVIOUSLY_NAME_STORAGE,songName);
        mEditor.putString(STRING_SONG_PLAYEDPREVIOUSLY_SONGARTIST_STORAGE, songArtist);
        mEditor.commit();

    }

    public void RetriveLastSavedSongData ()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SoundCloud_Class.prefsFile_Name,MODE_PRIVATE);
        singleSongItem songItemTemp = new singleSongItem();
        songItemTemp.setSongTitle(sharedPreferences.getString(STRING_SONG_PLAYEDPREVIOUSLY_NAME_STORAGE,"Select A Song to Play"));
        songItemTemp.setSongArtistTile(sharedPreferences.getString(STRING_SONG_PLAYEDPREVIOUSLY_SONGARTIST_STORAGE,"No Artist to this Song"));
        if (mInterface!=null)
        {
            mInterface.Set_MainActivity_BottomInfromer_SongData_ForFirstTime(songItemTemp);
        }

    }

    //Public Method used by activity to alot/Register the implemented Interface (Main activity will do this At the moment)
    public void registerInterface (MusicServiceCallBackInterface theImplementation)
    {
        mInterface = theImplementation;
        RetriveLastSavedSongData();
    }

    public void registerInterfaceForsongPlayBackActivity (songPlayBackServiceCallBackInterface theImplementation)
    {
        mSongPlayBackInterface= theImplementation;
    }

}
