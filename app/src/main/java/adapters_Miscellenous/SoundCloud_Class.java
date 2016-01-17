package adapters_Miscellenous;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.shoaib.gomusic.MainActivity;
import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Token;

import java.io.IOException;

/**
 * Created by Shoaib on 12/29/2015.
 */
public class SoundCloud_Class {

    //private static File WRAPPER_SER =  new File("wrapper.ser");
    private static SoundCloud_Class sInstance = null;
    private static Context mGlobalContext = null;

    public static final String prefsFile_Name="ApplicationPrefs";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor mFileEditor;

    private static final String CLIENT_ID =  "ee2fd5617384dcb0a2de793631a3c0bd";
    private static final String CLIENT_Secret ="6025e633cb5320218715a98a1a901d57";
    private static ApiWrapper wrapper;


    private static final String username_String="username";
    private static final String password_String="password";
    private static final String token_String="login_Token";
    private static final String onceLoggedIn_String ="first_Login";

    private SoundCloud_Class(Context context) {

        mGlobalContext=context;
        sharedPreferences= mGlobalContext.getSharedPreferences(prefsFile_Name, Context.MODE_PRIVATE);
        mFileEditor= sharedPreferences.edit();
        wrapper=new ApiWrapper(CLIENT_ID, CLIENT_Secret,null,null);
        //mAppContentFile= S
    }

    public static boolean getOnceLoggedIn ()
    {
        return sharedPreferences.getBoolean(onceLoggedIn_String,false);
    }

    public void saveUserCredentials(String username,String password)
    {

        mFileEditor.putString(username_String,username);
        mFileEditor.putString(password_String,password);
        //Set the status that user has logged in first time, so further we dont ask him anything
        mFileEditor.putBoolean(onceLoggedIn_String,true);
        mFileEditor.commit();

    }

    public void saveToken (Token token)
    {
        mFileEditor.putString(token_String,token.toString());
        mFileEditor.commit();

    }

    public void initiateUserLogin (String userName,String password)
    {
       //SoundCloud_Class.backAsyncTask mback;
        if (!sharedPreferences.getBoolean(onceLoggedIn_String,false))
        {
            new backAsyncTask().execute(userName,password,Token.SCOPE_NON_EXPIRING);
            saveUserCredentials(userName, password);
        }
        else
        {
            Toast.makeText(Application_Class.getAppContext(),"ALREADY SIGNED IN NO NEED AGAIN",Toast.LENGTH_LONG).show();
        }



    }


    public static SoundCloud_Class getInstance()
    {
        if (sInstance==null)
        {
            sInstance = new SoundCloud_Class(Application_Class.getAppContext());


        }

        return sInstance;
    }


    public class backAsyncTask extends AsyncTask<String,Void,Token>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Token doInBackground(String... strings)
        {
            int length = strings.length;
            Token token;
            try {
                token=wrapper.login(strings[0],strings[1],strings[2]);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return token;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Token token) {
            super.onPostExecute(token);

            //Save the token to the SharedPreferences
            if (token==null)
            {
                Toast.makeText(Application_Class.getAppContext(),"Login Failed: Try Again with valid Username and Password", Toast.LENGTH_LONG).show();
            }
            else
            {
                saveToken(token);
                Toast.makeText(Application_Class.getAppContext(),token+"The Token I Got", Toast.LENGTH_LONG).show();
                //sending call to the static Method in soundCloudFragment instantiateLoggedInFragment() to bring in new
                //Fragment.
                // soundCloudFragment.instantiateLoggedInFragment();
                MainActivity.instantiateLoggedInFragment();

            }


        }
    }


}
