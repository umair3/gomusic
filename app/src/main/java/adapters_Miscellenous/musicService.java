package adapters_Miscellenous;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoaib on 9/25/2015.
 */
public class musicService extends Service implements MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mMediaPlayer;

    private int songPosition=0;
    private List<singleSongItem> mSongsList;
    private final musicBinder mMusicBinder = new musicBinder();

    // Here Starts the implementation of the Service itself

    @Override
    public void onCreate() {
        super.onCreate();

        songPosition =0;
        mMediaPlayer= new MediaPlayer();
        initmMediaPlayer();

    }


    public class musicBinder extends Binder {

        public musicService getServiceInstanceForClient(){

            return musicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

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


    public void playSong (){
        mMediaPlayer.reset();
        singleSongItem tempSong = mSongsList.get(songPosition);
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,tempSong.getSongID());

        try{

            mMediaPlayer.setDataSource(getApplicationContext(),trackUri);

        }
        catch (Exception e){

            Log.e("MUSIC SERVICE", "Error setting data source", e);

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


}
