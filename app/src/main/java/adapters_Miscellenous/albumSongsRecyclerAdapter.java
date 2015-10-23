package adapters_Miscellenous;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shoaib.gomusic.R;
import com.example.shoaib.gomusic.singleAlbumItem;
import com.example.shoaib.gomusic.singleSongItem;

import java.util.List;

/**
 * Created by Shoaib on 8/17/2015.
 */
public class albumSongsRecyclerAdapter extends RecyclerView.Adapter<albumSongsRecyclerAdapter.albumActivityRecyclerViewHolder> {


    private Context context;
    LayoutInflater inflater;
    private List<singleSongItem> songsList;

    public albumSongsRecyclerAdapter () {



    }


    public albumSongsRecyclerAdapter (Context context, List<singleSongItem>
                                      songsList){

        this.context = context;
        this.songsList = songsList;
        inflater = LayoutInflater.from(context);



    }
    @Override
    public albumActivityRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = inflater.inflate(R.layout.single_song_item, parent, false);
        // creating a viewHolder Object and initializing it, by passing a view object in the
        // constructor.
        albumActivityRecyclerViewHolder holder = new albumActivityRecyclerViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(albumActivityRecyclerViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {


        return songsList.size();
    }


    class albumActivityRecyclerViewHolder extends RecyclerView.ViewHolder {


        TextView songName;
        TextView songDescription;

        public albumActivityRecyclerViewHolder(View itemView) {
            super(itemView);

            songName = (TextView) itemView.findViewById(R.id.songName);
            songDescription = (TextView) itemView.findViewById(R.id.songDescription);
        }
    }
}
