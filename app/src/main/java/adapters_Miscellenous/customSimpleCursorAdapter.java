package adapters_Miscellenous;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

import com.example.shoaib.gomusic.R;

/**
 * Created by Shoaib on 9/24/2015.
 */
public class customSimpleCursorAdapter extends SimpleCursorAdapter {
    public customSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);


         this.setViewBinder(new ViewBinder() {
             @Override
             public boolean setViewValue(View view, Cursor cursor, int columnIndex) {



                     if (view.getId()==R.id.songTitleProper)
                     {
                         ((TextView) view).setText(cursor.getString(cursor.getColumnIndex("TITLE")));
                         return true;

                     }

                     else if(view.getId()==R.id.songArtistProper)
                     {
                         ((TextView) view).setText(cursor.getString(cursor.getColumnIndex("ARTIST")));
                         return true;


                     }

                     else if (view.getId()==R.id.songDuration || view.getId()==R.id.songDuration2)
                     {
                         int durationInt=  cursor.getInt(cursor.getColumnIndex("DURATION"));
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

                         //data.
                        ((TextView) view).setText(actualDuration);
                         return true;


                     }
                 else{

                         return false;

                 }

             }
         });

    }


}
