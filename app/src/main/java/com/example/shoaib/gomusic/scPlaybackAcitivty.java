package com.example.shoaib.gomusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import adapters_Miscellenous.Application_Class;
import layout_Components.asutMediaController;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class scPlaybackAcitivty extends AppCompatActivity implements asutMediaController.Controllers_asutMediaController{

    private ArrayList<soundCloudSingleAudioItem> mAllSongsContent;
    private soundCloudSingleAudioItem currentSong;
    private asutMediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_playback_acitivty);

        mAllSongsContent= new ArrayList<soundCloudSingleAudioItem>();
        mediaController = (asutMediaController) findViewById(R.id.myMediaController);

        if (getIntent().getExtras().getString(Application_Class.Tag_CallerFor_scPlaybackActivity,"cantSay").equals(Application_Class.Name_Fragment_SC))
        {
            Bundle b = getIntent().getExtras();
            ArrayList<soundCloudSingleAudioItem> mList = b.getParcelableArrayList(Application_Class.Tag_soundCloudAudioItemsArrayList_toPass);
            mAllSongsContent.addAll(mList);
            currentSong = new soundCloudSingleAudioItem(mAllSongsContent.get(b.getInt(Application_Class.Tag_scFragmentList_CurrentItemIndex)));


        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    @Override
    public void play() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void playPrevious() {

    }

    @Override
    public void seekBarInteracted(int pos) {

    }

    @Override
    public boolean shuffle() {
        return false;
    }

    @Override
    public int repeat() {
        return 0;
    }
}
