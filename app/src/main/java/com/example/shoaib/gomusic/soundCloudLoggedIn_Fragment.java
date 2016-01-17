package com.example.shoaib.gomusic;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.ArrayList;

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
        wait_forSongs_IndicatorBar.setMessage("Fetching Audio Content");
        wait_forSongs_IndicatorBar.setTitle("SoundCloud Query");
        mSoundCloudAudioList = new ArrayList<soundCloudSingleAudioItem>();
        soundCloudSingleAudioItem mSampleItem = new soundCloudSingleAudioItem();
        mSampleItem.setAudioTitle("Search Desired Content in the Search Bar");
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

        @Override
        public void onBindViewHolder(scRecyclerAdapter.viewHolder holder, int position) {

            soundCloudSingleAudioItem scTempItem = scAudioList.get(position);

            holder.scAudioArt.setImageResource(R.drawable.ic_album_black_48dp);
            holder.scAudioArt.setAlpha(0.5f);


            (holder.scAudioTitle).setText(scTempItem.getAudioTitle());
            (holder.scAudioFrom).setText(String.valueOf(scTempItem.getSharedType()));
            (holder.scAudioDuration).setText("just Testing");
        }

        @Override
        public int getItemCount() {
            return scAudioList.size() ;
        }
    }


}


