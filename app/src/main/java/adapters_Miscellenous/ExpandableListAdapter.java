//package adapters_Miscellenous;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.shoaib.gomusic.R;
//import com.example.shoaib.gomusic.singleAlbumItem;
//import com.example.shoaib.gomusic.singleArtistItem;
//
//import java.util.List;
//
///**
// * Created by Shoaib on 8/14/2015.
//// */
//public class ExpandableListAdapter extends BaseExpandableListAdapter {
//
//    private Context context;
//    private List<singleAlbumItem> artistsNamesList;
//    LayoutInflater inflater;
//
//
//    public ExpandableListAdapter(List<singleArtistItem> theAlbumsList, Context theContext){
//
//        context = theContext;
//        albumsList=theAlbumsList;
//        inflater = LayoutInflater.from(theContext);
//
//
//    }
//
//    @Override
//    public int getGroupCount() {
//
//        return albumsList.size();
//    }
//
//    @Override
//    public int getChildrenCount(int position) {
//
//        return albumsList.get(position).getAlbumTotalSongs();
//    }
//
//    @Override
//    public Object getGroup(int position) {
//
//
//        return albumsList.get(position);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPostion) {
//
//        return albumsList.get(groupPosition).getSongsInAlbum().get(childPostion);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//
//        return albumsList.get(groupPosition).getAlbumID();
//
//       // return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPostion) {
//
//       return albumsList.get(groupPosition).getSongsInAlbum().get(childPostion).getSongID();
//
//       // return childPostion;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//
//        return false;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
//
//        //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        view = inflater.inflate(R.layout.album_heading_file,null);
//
//        TextView albumName = (TextView) view.findViewById(R.id.albumTitleText);
//        ImageView albumArt = (ImageView) view.findViewById(R.id.albumCoverArt);
//
//        albumName.setText(albumsList.get(groupPosition).getAlbumTitle());
//        albumArt.setImageResource(albumsList.get(groupPosition).getAlbumArtID());
//
//        return view;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
//
//        view = inflater.inflate(R.layout.album_child_layout,null);
//
//        TextView songTitle = (TextView) view.findViewById(R.id.albumChildViewText);
//
//        songTitle.setText(albumsList.get(groupPosition).getSongsInAlbum().get(childPosition).getSongTitle());
//
//        return view;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//
//        return true;
//    }
//}
