package com.example.shoaib.gomusic;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import adapters_Miscellenous.Network_VolleySingleton;
import adapters_Miscellenous.scResponseKeys;


public class soundCloudLoggedIn_Fragment extends android.support.v4.app.Fragment {

    private ProgressDialog wait_forSongs_IndicatorBar;
    private RecyclerView mScRecylerList;
    private scRecyclerAdapter mRecyclerAdapter;

    private final String requestUrl = "http://api.soundcloud.com/tracks?client_id=ee2fd5617384dcb0a2de793631a3c0bd&q=";
    private RequestQueue queue;

    private ArrayList<soundCloudSingleAudioItem> mSoundCloudAudioList;


    public soundCloudLoggedIn_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wait_forSongs_IndicatorBar = new ProgressDialog(getActivity());
        wait_forSongs_IndicatorBar.setMessage("Searching on SoundCloud...");
        wait_forSongs_IndicatorBar.setTitle("Searching...");

        // Maintain a list of recently played SoundCloud items and
        // Show list of recently played SoundCloud items by default.
        mSoundCloudAudioList = new ArrayList<soundCloudSingleAudioItem>();
        soundCloudSingleAudioItem mSampleItem = new soundCloudSingleAudioItem();
        mSampleItem.setAudioTitle("Show recently played SoundCloud items here.");
        mSampleItem.setWhetherAudioStreamable(true);
        mSampleItem.setSharedType("Public");
        mSoundCloudAudioList.add(mSampleItem);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_sound_cloud_logged_in, container, false);
        mScRecylerList = (RecyclerView) layout.findViewById(R.id.scRecylerView);
        mRecyclerAdapter = new scRecyclerAdapter(getContext(),mSoundCloudAudioList);
        mScRecylerList.setAdapter(mRecyclerAdapter);
        mScRecylerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public void searchStringForRequest(String charSequence) {

        wait_forSongs_IndicatorBar.show();
        if (queue == null) {
            queue = Network_VolleySingleton.getInstance().getRequestQueue();
        }

        String finalRequestString = requestUrl + charSequence;
        finalRequestString = Network_VolleySingleton.queryChecker(finalRequestString);
        JsonArrayRequest mRequestSongsWithGivenQuery = new JsonArrayRequest(Request.Method.GET, finalRequestString, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                parseJsonResponse(response);
                if(wait_forSongs_IndicatorBar.isShowing())
                {
                    wait_forSongs_IndicatorBar.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(wait_forSongs_IndicatorBar.isShowing())
                {
                    wait_forSongs_IndicatorBar.dismiss();
                }
                Toast.makeText(getActivity(), "Error aya he", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(mRequestSongsWithGivenQuery);
    }


    public void parseJsonResponse(JSONArray mResponse) {
        int returnedJSonArrayLength = mResponse.length();
        mSoundCloudAudioList.clear();
        for (int i = 0; i < returnedJSonArrayLength; i++) {
            soundCloudSingleAudioItem mTempItem = new soundCloudSingleAudioItem();

            try {
                JSONObject mTempObject = mResponse.getJSONObject(i);
                mTempItem.setAudioTitle(mTempObject.getString(scResponseKeys.SongObjectEndpoints.Key_audioTitle));
                mTempItem.setSharedType(mTempObject.getString(scResponseKeys.SongObjectEndpoints.key_whetherAudioPublicShared));
                mTempItem.setWhetherAudioStreamable(mTempObject.getBoolean(scResponseKeys.SongObjectEndpoints.Key_audioStreamable));
                mTempItem.setAudioStreamingUrL(mTempObject.getString(scResponseKeys.SongObjectEndpoints.Key_audioStreamingUrL));
                mTempItem.setArtWorkUrl(mTempObject.getString(scResponseKeys.SongObjectEndpoints.Key_artWork));
                mTempItem.setAudioDuration(mTempObject.getLong(scResponseKeys.SongObjectEndpoints.Key_audioDuration));
                mSoundCloudAudioList.add(mTempItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        UpdateRecyclerView();
        System.out.print("hhaa");
    }

    public void UpdateRecyclerView()
    {
        mRecyclerAdapter.scAudioList.clear();
        mRecyclerAdapter.notifyDataSetChanged();
        mRecyclerAdapter.scAudioList.addAll(mSoundCloudAudioList);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    public class scRecyclerAdapter extends RecyclerView.Adapter<scRecyclerAdapter.viewHolder> {
        public ArrayList<soundCloudSingleAudioItem> scAudioList;
        private LayoutInflater layoutInflater;
        private Context context;
        public ArrayList<soundCloudSingleAudioItem> scAdapter_Unchanged_Unmodified_SongsList;


        public scRecyclerAdapter (ArrayList<soundCloudSingleAudioItem> mList)
        {
            this.scAudioList = new ArrayList<soundCloudSingleAudioItem>();
            this.scAudioList.addAll(mList);
            this.scAdapter_Unchanged_Unmodified_SongsList = new ArrayList<soundCloudSingleAudioItem>();
            this.scAdapter_Unchanged_Unmodified_SongsList.addAll(mList);
            this.scAdapter_Unchanged_Unmodified_SongsList = mList;

        }

        public scRecyclerAdapter (Context mContext,ArrayList<soundCloudSingleAudioItem> mList )
        {
            this.context = mContext;
            this.scAudioList = new ArrayList<soundCloudSingleAudioItem>();
            this.scAudioList.addAll(mList);
            this.scAdapter_Unchanged_Unmodified_SongsList = new ArrayList<soundCloudSingleAudioItem>();
            this.scAdapter_Unchanged_Unmodified_SongsList.addAll(mList);
            this.scAdapter_Unchanged_Unmodified_SongsList = mList;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            ImageView scAudioArt;
            TextView scAudioTitle;
            TextView scAudioFrom;
            TextView scAudioDuration;

            public viewHolder(View itemView) {
                super(itemView);

                scAudioArt = (ImageView) itemView.findViewById(R.id.albumCoverPhoto);
                scAudioTitle = (TextView) itemView.findViewById(R.id.albumTitleText);
                scAudioFrom = (TextView) itemView.findViewById(R.id.albumSingerText);
                scAudioDuration = (TextView) itemView.findViewById(R.id.albumTotalSongsText);
            }
        }

        @Override
        public scRecyclerAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View oneRowView = layoutInflater.inflate(R.layout.single_album_item, parent, false);
            // Return a new holder instance
            viewHolder mViewHolder = new viewHolder(oneRowView);
            return mViewHolder;

        }



        public boolean loadImageFromURL(String fileUrl,
                                        ImageView iv){
            try {


                URL myFileUrl = new URL (fileUrl);
                HttpURLConnection conn =
                        (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                iv.setImageBitmap(BitmapFactory.decodeStream(is));

                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        public void onBindViewHolder(final scRecyclerAdapter.viewHolder holder, int position) {

            soundCloudSingleAudioItem scTempItem = scAudioList.get(position);

            final String imageURL = scTempItem.getArtWorkUrl();

            if(imageURL != null && !imageURL.isEmpty()){
                //holder.scAudioArt.setImageURI(Uri.parse(scTempItem.getArtWorkUrl()));
                //holder.scAudioArt.setImageURI();
                //holder.scAudioArt.setAlpha(0.5f);
                Runnable runnable = new Runnable() {
                    public void run() {
                        //Code adding here...
                        loadImageFromURL(imageURL,holder.scAudioArt);
                    }
                };
                new Thread(runnable).start();

                loadImageFromURL(imageURL,holder.scAudioArt);
                //holder.scAudioArt.setImageResource(R.drawable.ic_album_black_48dp);

            } else {
                holder.scAudioArt.setImageResource(R.drawable.ic_album_black_48dp);
            }

            (holder.scAudioTitle).setText(scTempItem.getAudioTitle());
            (holder.scAudioFrom).setText(String.valueOf(scTempItem.getSharedType()));

            long millis = (long)scTempItem.getAudioDuration();
            String hms;
            if (TimeUnit.MILLISECONDS.toHours(millis) > 0) {

                hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            }else{
                hms = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            }



            (holder.scAudioDuration).setText(hms);
        }

        @Override
        public int getItemCount() {
            return scAudioList.size() ;
        }
    }


}


