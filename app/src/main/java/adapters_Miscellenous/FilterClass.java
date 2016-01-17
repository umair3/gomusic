package adapters_Miscellenous;

import android.widget.Filter;

import com.example.shoaib.gomusic.singleSongItem;

import java.util.ArrayList;

/**
 * Created by Shoaib on 1/3/2016.
 */
public class FilterClass extends Filter {

    recyclerAdapter mAdapter;
    ArrayList<singleSongItem> mArrayList;
    ArrayList<singleSongItem> mFilteredList;
    public FilterClass (recyclerAdapter mAdapter, ArrayList<singleSongItem> mSongsList)
    {
        this.mAdapter = mAdapter;
        this.mArrayList = mSongsList;
        this.mFilteredList= new ArrayList<singleSongItem>();

    }


    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        mFilteredList.clear();
        FilterResults results= new FilterResults();

        if (charSequence.length()==0)
        {
            mFilteredList.addAll(mAdapter.UnchangedUnmodifiedSongsList);
        }
        else
        {
            final String filterPattern = charSequence.toString().toLowerCase().trim();

            for (int position=0; position<mAdapter.UnchangedUnmodifiedSongsList.size();position++)
            {
                if ((mAdapter.UnchangedUnmodifiedSongsList.get(position).getSongTitle().toLowerCase().contains(charSequence.toString().toLowerCase()))||(mAdapter.UnchangedUnmodifiedSongsList.get(position).getSongArtistTile().toLowerCase().contains(charSequence.toString().toLowerCase()))){

                    mFilteredList.add(mAdapter.UnchangedUnmodifiedSongsList.get(position));

                }

            }

        }
        results.values = mFilteredList;
        results.count = mFilteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        mAdapter.songsList.clear();
        if (charSequence.length()==0)
        {
            mAdapter.songsList.addAll(mAdapter.UnchangedUnmodifiedSongsList);
        }
        else
        {
            mAdapter.songsList.addAll((ArrayList<singleSongItem>) filterResults.values);
        }
        mAdapter.notifyDataSetChanged();
    }
}
