package com.example.shoaib.gomusic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shoaib on 8/15/2015.
 */
public class singleAlbumItem implements Parcelable{

    private int albumID;
    private int albumTotalSongs;
    private String albumTitle;
    private String albumSingerName;
    private String BitmapPath;


    public singleAlbumItem () {



    }

    public singleAlbumItem (int theAlbumID, int theAlbumTotalSongs,
                            String theAlbumTitle, String theAlbumSingerName) {

        this.albumID = theAlbumID;
        this.albumTotalSongs= theAlbumTotalSongs;
        this.albumTitle = theAlbumTitle;
        this.albumSingerName = theAlbumSingerName;
    }


    // Setters............

    public void setAlbumID (int theAlbumID) {

        albumID = theAlbumID;

    }

    public void setAlbumTotalSongs (int theAlbumTotalSongs) {

        albumTotalSongs = theAlbumTotalSongs;

    }

    public void setAlbumTitle (String theAlbumTitle) {

        albumTitle = theAlbumTitle;

    }

    public void setAlbumSingerName(String theAlbumSingerName){

        this.albumSingerName= theAlbumSingerName;
    }

    public void setBitmapPath (String givenBitmap){

        this.BitmapPath = givenBitmap;
    }


    // Getters...............

    public int getAlbumID () {

        return this.albumID;

    }


    public int getAlbumTotalSongs () {

        return this.albumTotalSongs;

    }

    public String getAlbumTitle () {

        return this.albumTitle;

    }

    public String getAlbumSingerName () {

        return this.albumSingerName;
    }

    public String getBitmapPath (){

        return BitmapPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.albumID);
        parcel.writeInt(this.albumTotalSongs);
        parcel.writeString(this.albumTitle);
        parcel.writeString(this.albumSingerName);
        parcel.writeString(this.BitmapPath);

    }

    public static final Creator<singleAlbumItem> CREATOR = new Creator<singleAlbumItem>() {
        @Override
        public singleAlbumItem createFromParcel(Parcel parcel) {
            return new singleAlbumItem(parcel);
        }

        @Override
        public singleAlbumItem[] newArray(int i) {
            return new singleAlbumItem[i];
        }
    };

    private singleAlbumItem (Parcel parcelIn)
    {
        this.albumID = parcelIn.readInt();
        this.albumTotalSongs = parcelIn.readInt();
        this.albumTitle = parcelIn.readString();
        this.albumSingerName = parcelIn.readString();
        this.BitmapPath = parcelIn.readString();


    }
}
