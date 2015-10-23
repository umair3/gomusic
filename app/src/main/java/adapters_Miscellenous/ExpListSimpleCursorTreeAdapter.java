package adapters_Miscellenous;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.SimpleCursorTreeAdapter;

import com.example.shoaib.gomusic.MainActivity;
import com.example.shoaib.gomusic.artistsFragment;

/**
 * Created by Shoaib on 10/11/2015.
 */
//public class ExpListSimpleCursorTreeAdapter extends SimpleCursorTreeAdapter {
//
//   Context mContext;
//
//    public ExpListSimpleCursorTreeAdapter(Context context,int groupLayout, String[] groupFrom, int[] groupTo, int childLayout, String[] childFrom, int[] childTo) {
//        super(context,null,groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
//
//        mContext =context;
//
//}
//
//    @Override
//    protected Cursor getChildrenCursor(Cursor cursor) {
//
//        final int groupID = cursor.getInt(cursor.getColumnIndex("_ID"));
//        Cursor childCursor = null;
//        try {
//            final String sortOrder = MediaStore.Audio.Media.TITLE +" ASC";
//            final CursorLoader cursorLoader = new CursorLoader(
//                    mContext,
//                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                    ShoppingListTable.FIELD_LIST,
//                    "(" + DbVar.PRODUCT_SUB_CATEGORY_ID + "=?)",
//                    new String[]{ productSubCategoryId + ""}, sortOrder);
//            cursorLoader.registerListener(0, this);
//            childCursor = cursorLoader.loadInBackground();
//            childCursor.moveToFirst();
//
//        } catch (final Exception e) {
//            e.printStackTrace();
//        }
//
//        return childCursor;
//    }
//}
