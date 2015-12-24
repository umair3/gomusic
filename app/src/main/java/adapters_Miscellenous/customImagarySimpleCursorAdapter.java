package adapters_Miscellenous;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoaib.gomusic.R;

/**
 * Created by Shoaib on 9/24/2015.
 */
public class customImagarySimpleCursorAdapter extends SimpleCursorAdapter {


    public customImagarySimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

       // Bitmap defArt = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_album_black_48dp);
        //final Bitmap toDefArt = getRoundedShape(defArt);

        this.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                if (view.getId() == R.id.albumTitleText) {

                    ((TextView) view).setText(cursor.getString(cursor.getColumnIndex("ALBUM")));
                    return true;

                } else if (view.getId() == R.id.albumSingerText) {

                    ((TextView) view).setText(cursor.getString(cursor.getColumnIndex("ARTIST")));
                    return true;

                } else if (view.getId() == R.id.albumTotalSongsText) {

                    ((TextView) view).setText(cursor.getString(cursor.getColumnIndex("numsongs")));
                    return true;

                }
                else if (view.getId() == R.id.albumCoverPhoto) {



                    ImageView albumart = (ImageView) view.findViewById(R.id.albumCoverPhoto);
                    String art_Path = cursor.getString(cursor.getColumnIndex("album_art"));
                    Bitmap art;

                    if (art_Path!=null)
                    {
                        art = BitmapFactory.decodeFile(art_Path);
                        if (art != null) {

                            //art = getRoundedShape(art);
                            ImageView mView = ((ImageView)view);
                            mView.setImageBitmap(art);
                            mView.setAlpha(1.0f);
                           //((ImageView)view).setImageBitmap(art);
                        }
                        else{

                            ImageView mView = ((ImageView)view);
                            mView.setAlpha(0.5f);
                            mView.setImageResource(R.drawable.ic_album_black_48dp);
                         //   ((ImageView)view).setImageBitmap(toDefArt);
                         //   ((ImageView)view).setAlpha(57.0f);
                        }
                        return true;

                    }
                    else
                    {
                        ImageView mView = ((ImageView)view);
                        mView.setAlpha(0.5f);
                        mView.setImageResource(R.drawable.ic_album_black_48dp);

                    }
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }



    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 120;
        int targetHeight = 120;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
}
