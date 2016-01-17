package adapters_Miscellenous;

import android.widget.Filter;

import com.example.shoaib.gomusic.albumsFragment;
import com.example.shoaib.gomusic.singleAlbumItem;

import java.util.ArrayList;

/**
 * Created by Shoaib on 1/3/2016.
 */
public class albumFilterClass extends Filter {

    albumsFragment.albumRecyclerAdapter mAdapter;
    ArrayList<singleAlbumItem> mArrayList= new ArrayList<singleAlbumItem>();
    ArrayList<singleAlbumItem> mFilteredList;
    public albumFilterClass (albumsFragment.albumRecyclerAdapter mAdapter, ArrayList<singleAlbumItem> mAlbumsList)
    {
        this.mAdapter = mAdapter;
        this.mArrayList = mAlbumsList;
        this.mFilteredList= new ArrayList<singleAlbumItem>();

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
                if ((mAdapter.UnchangedUnmodifiedSongsList.get(position).getAlbumTitle().toLowerCase().contains(charSequence.toString().toLowerCase()))){

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
        mAdapter.theAlbumsList.clear();
        if (charSequence.length()==0)
        {
            mAdapter.theAlbumsList.addAll(mAdapter.UnchangedUnmodifiedSongsList);
        }
        else
        {
            mAdapter.theAlbumsList.addAll((ArrayList<singleAlbumItem>) filterResults.values);
        }
        mAdapter.notifyDataSetChanged();
    }
}
