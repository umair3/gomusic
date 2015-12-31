package com.example.shoaib.gomusic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;

import adapters_Miscellenous.SoundCloud_Class;


public class soundCloudFragment extends android.support.v4.app.Fragment {

    private static final String TAG ="soundCloudFragment";
    private static FragmentTransaction trans;

    //Interface that's not being used at the moment
    public interface from_SoundCloudFragment_SignedIn {

       void onSignIn_InvokeNewFragment ();
    }


    private from_SoundCloudFragment_SignedIn mCallBack;
    //private final String authorization_url = "https://soundcloud.com/connect?client_id=ee2fd5617384dcb0a2de793631a3c0bd&redirect_uri=gomusic://soundcloud/getcallbackhostedsoundcloud&response_type=code_and_token&scope=non-expiring&display=popup";
    private final String authorization_url="https://api.soundcloud.com/tracks?client_id=ee2fd5617384dcb0a2de793631a3c0bd";
    private RequestQueue mRequest;

    public soundCloudFragment() {
        // Required empty public constructor

    }

//    public soundCloudFragment(from_SoundCloudFragment_SignedIn theListener) {
//        // Required empty public constructor
//        mCallBack =theListener;
//    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
          //  mCallBack = (from_SoundCloudFragment_SignedIn) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()+"must implement from_SoundCloudFragment_SignedIn Listener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_sound_cloud, container, false);


        android.support.v7.widget.AppCompatButton mButton =(AppCompatButton) layout.findViewById(R.id.soundCloudAuthButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    SoundCloud_Class.getInstance().initiateUserLogin("sherdil04@gmail.com","185442114764dp");
                    //instantiateLoggedInFragment();
            }
        });

        return layout;

    }



    // Method responsible for bringin in the new fragment when the user has signed in
    // this is called from the SoundCloud_class.java , when the background thread
    // completes the sign in process.
//    public void instantiateLoggedInFragment ()
//    {
//				/*
//				 * IMPORTANT: We use the "root frame" defined in
//				 * "root_fragment.xml" as the reference to replace fragment
//				 */
//        FragmentTransaction trans = getFragmentManager().beginTransaction();
//                trans.replace(R.id.root_frame, new soundCloudLoggedIn_Fragment());
//
//				/*
//				 * IMPORTANT: The following lines allow us to add the fragment
//				 * to the stack and return to it later, by pressing back
//				 */
//        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        trans.addToBackStack(null);
//
//        trans.commit();
//    }

}