package adapters_Miscellenous;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoaib.gomusic.R;
import com.example.shoaib.gomusic.singleSongItem;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Shoaib on 8/8/2015.
 */
public class songsListRecyclerAdapter extends RecyclerView.Adapter<songsListRecyclerAdapter.forSongsListRecyclerAdapter> {

        private LayoutInflater inflater;
        private List <singleSongItem> songData;
        private Context context;

    public songsListRecyclerAdapter(Context context,List<singleSongItem> thesongData) {

        inflater = LayoutInflater.from(context);

        songData = thesongData;

        this.context = context;


    }

    @Override
    public forSongsListRecyclerAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        // When this Adapter is passed to a RecyclerView this function is called only Once
        // At the time of creation, since to cache the view we create ViewHolder Object
        // Here in this Function , so that it would be called only once and ViewHolder object
        // which uses findViewById within it would also be created only once.

        View view;
        view = inflater.inflate(R.layout.single_song_item, parent, false);
        // creating a viewHolder Object and initializing it, by passing a view object in the
        // constructor.
        forSongsListRecyclerAdapter holder = new forSongsListRecyclerAdapter(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(forSongsListRecyclerAdapter holder, int position) {

        singleSongItem currentSong = songData.get(position);
        holder.songName.setText(currentSong.getSongTitle());
        holder.songDescription.setText(currentSong.getSongArtistTile());
       // holder.songIcon.setImageResource(currentSong.getSongIconId());

    }

    @Override
    public int getItemCount() {

        return songData.size();
    }


    class forSongsListRecyclerAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView songName;
        TextView songDescription;

        public forSongsListRecyclerAdapter(View itemView) {
            super(itemView);

            songName = (TextView) itemView.findViewById(R.id.songName);
            songDescription = (TextView) itemView.findViewById(R.id.songDescription);
            itemView.setOnClickListener(this);
           // songIcon = (ImageView) itemView.findViewById(R.id.songIcon);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(context,"Hello Item Clicked",Toast.LENGTH_SHORT).show();

        }
    }
}
