package adapters_Miscellenous;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoaib.gomusic.R;

import org.w3c.dom.Text;

/**
 * Created by Shoaib on 9/20/2015.
 */
public class MyCursorAdapter extends CursorAdapter {

    private Context mContext;
    private Cursor mCursor;

    static class MyViewHolder{

        TextView vhAlbumTitle;
        TextView vhAlbumSinger;


    }


    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        this.mContext= context;
        this.mCursor= c;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.single_album_item,viewGroup,false); // You might need to give it a Look

        MyViewHolder holder = new MyViewHolder();
        holder.vhAlbumTitle = (TextView) rowView.findViewById(R.id.albumTitleText);
        holder.vhAlbumSinger= (TextView) rowView.findViewById(R.id.albumSingerText);



        rowView.setTag(holder);


        return rowView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        MyViewHolder holder = (MyViewHolder) view.getTag();




            int idColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums._ID);
            int titleColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM);
            int artColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);


            long thisId = cursor.getLong(idColumn);
            String thisTitle = cursor.getString(titleColumn);
            //String thisArt = cursor.getString(artColumn);
            holder.vhAlbumTitle.setText(thisTitle);
            holder.vhAlbumSinger.setText("For testing code");



           // cursor.moveToNext();


    }

}