package adapters_Miscellenous;

import android.app.Application;
import android.content.Context;

/**
 * Created by Shoaib on 12/28/2015.
 */
public class Application_Class extends Application {

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
