package com.example.shoaib.gomusic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Shoaib on 10/11/2015.
 */
public class singleArtistItem implements Parcelable {


    private String artistItem_Title;
    private int artistItem_TotalSongs;
    private ArrayList<singleSongItem> artistItem_songsList;

    public singleArtistItem () {


    }

    public singleArtistItem (String artistItem_Title, int artistItem_TotalSongs, ArrayList<singleSongItem> artistItem_songsList){

        this.artistItem_Title = artistItem_Title;
        this.artistItem_TotalSongs = artistItem_TotalSongs;
        this.artistItem_songsList = artistItem_songsList;

    }

    public void setArtistItem_Title (String arTitle){

        this.artistItem_Title = arTitle;
    }

    public void setArtistItem_TotalSongs (int arTotalSongs){

        this.artistItem_TotalSongs = arTotalSongs;

    }

    public void setArtistItem_songsList (ArrayList<singleSongItem> arSongsList){

        this.artistItem_songsList = arSongsList;
    }


    public String getArtistItem_Title () {

        return this.artistItem_Title;

    }

    public int getArtistItem_TotalSongs (){


        return this.artistItem_TotalSongs;

    }

    public ArrayList<singleSongItem> getArtistItem_SongsList () {


        return this.artistItem_songsList;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(artistItem_Title);
        parcel.writeInt(artistItem_TotalSongs);

    }

    public static final Creator<singleArtistItem> CREATOR = new Creator<singleArtistItem>() {
        @Override
        public singleArtistItem createFromParcel(Parcel parcel) {

            return  new singleArtistItem(parcel);
        }

        @Override
        public singleArtistItem[] newArray(int size) {

            return new singleArtistItem[size];
        }
    };

    private singleArtistItem (Parcel parcel) {

        this.artistItem_Title = parcel.readString();
        this.artistItem_TotalSongs= parcel.readInt();

    }
}
