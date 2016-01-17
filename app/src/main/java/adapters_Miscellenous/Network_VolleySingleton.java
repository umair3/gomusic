package adapters_Miscellenous;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Shoaib on 12/27/2015.
 */
public class Network_VolleySingleton {

    private static Network_VolleySingleton sVolleyInstance=null;
    private RequestQueue mRequestQueue;
    private static Context sGlobalContext;

    private Network_VolleySingleton (Context theContext)
    {
        sGlobalContext = theContext;
        mRequestQueue = Volley.newRequestQueue(sGlobalContext);

    }

    public static Network_VolleySingleton getInstance ()
    {
        if (sVolleyInstance==null)
        {
            sVolleyInstance = new Network_VolleySingleton(Application_Class.getAppContext());
        }

        return sVolleyInstance;

    }

    public RequestQueue getRequestQueue()
    {

        return mRequestQueue;
    }

    public static String queryChecker (String toCheck)
    {
        if (toCheck.contains(" "))
        {
            toCheck=toCheck.replaceAll(" ","%20");
        }
        return toCheck;

    }

}
