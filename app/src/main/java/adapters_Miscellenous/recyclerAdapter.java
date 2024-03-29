package adapters_Miscellenous;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.shoaib.gomusic.R;
import com.example.shoaib.gomusic.singleSongItem;
import com.example.shoaib.gomusic.songPlayBackAcitivty;

import java.util.ArrayList;

/**
 * Created by Shoaib on 8/17/2015.
 */
public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.rowViewHolder> implements Filterable {


    public boolean whether_To_ImplementClickListener=true;
    private Context context;
    private LayoutInflater inflater;
    public ArrayList<singleSongItem> songsList;
    public ArrayList<singleSongItem> UnchangedUnmodifiedSongsList;


    public class rowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // These are for ViewHolder
        TextView songName;
        TextView songArtist;
        TextView songDuration;
        private Context theContext;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public rowViewHolder(View itemView,Context mContext) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            theContext = mContext;
            songName = (TextView) itemView.findViewById(R.id.songTitleProper);
            songArtist = (TextView) itemView.findViewById(R.id.songArtistProper);
            songDuration = (TextView) itemView.findViewById(R.id.songDuration);
            if (whether_To_ImplementClickListener)
            {
                itemView.setOnClickListener(this);
            }



        }

        @Override
        public void onClick(View view) {
            final Intent mIntent = new Intent(context,songPlayBackAcitivty.class);
            Bundle contentBundle = new Bundle();
            singleSongItem clickedItem = songsList.get(getAdapterPosition());
            contentBundle.putString("Caller","songsFragment");
            contentBundle.putParcelable("songItemToSend", songsList.get(getAdapterPosition()));
            contentBundle.putInt("songToSet", getAdapterPosition());
            contentBundle.putParcelableArrayList("listOfSongs", songsList);
            Snackbar.make(itemView,songsList.get(getAdapterPosition()).getSongTitle(), Snackbar.LENGTH_SHORT).show();
            //Toast.makeText(context, songsList.get(getPosition()).getSongTitle(), Toast.LENGTH_SHORT).show();
            mIntent.putExtras(contentBundle);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    context.startActivity(mIntent);
                }
            }, 1000);
           // context.startActivity(mIntent);
        }
    }


    public recyclerAdapter() {



    }

    public recyclerAdapter(ArrayList<singleSongItem> mSongsList) {

        this.songsList = mSongsList;
        this.UnchangedUnmodifiedSongsList = new ArrayList<singleSongItem>();
        this.UnchangedUnmodifiedSongsList.addAll(mSongsList);

    }


    public recyclerAdapter(Context context, ArrayList<singleSongItem> theSongsList,boolean toImplement){

        this.context = context;
        this.songsList = theSongsList;
        this.UnchangedUnmodifiedSongsList = new ArrayList<singleSongItem>();
        this.UnchangedUnmodifiedSongsList.addAll(theSongsList);
        inflater = LayoutInflater.from(context);
        whether_To_ImplementClickListener = toImplement;



    }
    @Override
    public rowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view;
//        view = inflater.inflate(R.layout.single_song_item, parent, false);
//        // creating a viewHolder Object and initializing it, by passing a view object in the
//        // constructor.
//        rowViewHolder holder = new rowViewHolder(view);
//
//        return holder;

        //Context context = parent.getContext();
        //LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View oneRowView = inflater.inflate(R.layout.singlesong_proper_item, parent, false);

        // Return a new holder instance
        rowViewHolder mViewHolder = new rowViewHolder(oneRowView,context);
        return mViewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(rowViewHolder holder, int position) {

        singleSongItem mCurrentSongItem = songsList.get(position);

        (holder.songName).setText(mCurrentSongItem.getSongTitle());
        (holder.songArtist).setText(mCurrentSongItem.getSongArtistTile());

        //Converting song duration of miliseconds into Readable time
        int durationInt=  mCurrentSongItem.getSongDuration();
        int seconds =durationInt/(1000);//seconds
        seconds = seconds%60;
        int minutes = durationInt/(1000*60); //minutes

        String actualDuration;
        if (seconds<10){
            actualDuration = Integer.toString(minutes) + ":0" + Integer.toString(seconds);
        }
        else{
            actualDuration = Integer.toString(minutes) + ":" + Integer.toString(seconds);
        }
        (holder.songDuration).setText(actualDuration);


    }



    @Override
    public int getItemCount() {

        return songsList.size();
    }

    @Override
    public Filter getFilter() {
        return new FilterClass(this,songsList);
    }


}
