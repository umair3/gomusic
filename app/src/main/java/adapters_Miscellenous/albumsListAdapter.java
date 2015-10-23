package adapters_Miscellenous;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoaib.gomusic.R;
import com.example.shoaib.gomusic.singleAlbumItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoaib on 9/19/2015.
 */
public class albumsListAdapter extends BaseAdapter {

    private List<singleAlbumItem> mAlbumsList;
    private Context mContext;

    public albumsListAdapter (Context  context ,ArrayList<singleAlbumItem> albumsList) {

        this.mContext=context;
        mAlbumsList=albumsList;

    }

    @Override
    public int getCount() {

        return mAlbumsList.size();
    }

    @Override
    public singleAlbumItem getItem(int position) {
        return mAlbumsList.get(position);
    }

    // Give Below A Look Once If Any Error is seen
    @Override
    public long getItemId(int position) {
        return mAlbumsList.get(position).getAlbumID();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View albumElement = inflater.inflate(R.layout.single_album_item,viewGroup,false);
        TextView albumTitle = (TextView) albumElement.findViewById(R.id.albumTitleText);
        TextView albumSinger= (TextView) albumElement.findViewById(R.id.albumSingerText);


        albumTitle.setText(mAlbumsList.get(position).getAlbumTitle());
        albumSinger.setText("Singers to be Placed Here");


        return albumElement;
    }
}
