package adapters_Miscellenous;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shoaib on 12/28/2015.
 */
public class Application_Class extends Application {

    //Fields Common to whole App
    public static final String Name_Fragment_Songs = "songsFragment";
    public static final String Name_Fragment_Albums = "albumsFragment";
    public static final String Name_Fragment_SC = "soundCloudFragment";
    public static final String Name_Fragment_Artist = "artistsFragment";
    public static final String Name_Activity_DetailedActivity = "detailed_Activity";

    // SongPlayBack Activity specific Fields
    public static final String Tag_CallerFor_songPlayBackActivity = "Caller";


    // SounCloud Fragment and scPlayBack Activity specific
    public static final String Tag_CallerFor_scPlaybackActivity = "Caller";
    public static final String Tag_Not_Existing = "cant_say";
    public static final String Tag_soundCloudAudioItemsArrayList_toPass = "passScArrayList";
    public static final String Tag_scFragmentList_CurrentItemIndex = "currentItemIndex";

    private static Application_Class sInstance;
    private static SoundCloud_Class mSoundCloudInstance ;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mSoundCloudInstance = SoundCloud_Class.getInstance();
    }

    public static Application_Class getsInstance ()
    {

        return sInstance;
    }

    public static Context getAppContext ()
    {
        if (sInstance==null)
        {
            return null;
        }
        else
        {
            return sInstance.getApplicationContext();
        }

    }

}
