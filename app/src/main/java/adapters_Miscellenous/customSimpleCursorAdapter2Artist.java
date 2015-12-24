package adapters_Miscellenous;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

import com.example.shoaib.gomusic.R;

/**
 * Created by Shoaib on 10/16/2015.
 */
public class customSimpleCursorAdapter2Artist extends SimpleCursorAdapter {


    public customSimpleCursorAdapter2Artist(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);



        this.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {



                if (view.getId()== R.id.ParentViewText)
                {
                    ((TextView) view).setText(cursor.getString(cursor.getColumnIndex("artist")));
                    return true;

                }

                else if(view.getId()==R.id.ParentViewSongsFromArtistText)
                {
                    TextView mView = ((TextView)view);
                    mView.setText(cursor.getString(cursor.getColumnIndex("number_of_tracks"))+" song(s)");
                   // ((TextView) view).setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS)));
                    return true;


                }

                else{

                    return false;

                }

            }
        });

    }

}
