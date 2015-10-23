package com.example.shoaib.gomusic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shoaib on 8/8/2015.
 */
public class singleSongItem implements Parcelable {

    private long songID;
    private String songIconId;
    String songTitle;
    String songArtistTitle;
    private int songDuration;

    public singleSongItem () {


    }


    public singleSongItem (long thesongID,String theSongTitle, String theSongArtistTitle ) {

        songID = thesongID;
        songTitle = theSongTitle;
        songArtistTitle = theSongArtistTitle;

    }

    // ---------------setters------------

    public void setSongID (long theSongID){

        songID = theSongID;

    }

    public void setSongIconId (String  theSongIconId){

       songIconId = theSongIconId;

    }

    public void setSongTitle (String theSongTitle){

        songTitle = theSongTitle;

    }

    public void setSongArtistTile (String theSongArtistTitle){

        songArtistTitle = theSongArtistTitle;

    }

    public void setSongDuration(int givenDuration){

        songDuration = givenDuration;
    }

    // ----------------getters-------------

    public long getSongID (){

        return songID;

    }

//    public int getSongIconId (){
//
//      //  return songIconId;
//
//    }

    public int getSongDuration(){

        return songDuration;
    }

    public String getSongTitle (){

        return songTitle;

    }

    public String getSongArtistTile (){

        return songArtistTitle;

    }

    public String getSongIconId (){

        return songIconId;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(this.songID);
        parcel.writeString(this.songTitle);
        parcel.writeString(this.songArtistTitle);
        parcel.writeInt(this.songDuration);
        parcel.writeString(this.songIconId);
    }

    public static final Creator<singleSongItem> CREATOR = new Creator<singleSongItem>() {
        @Override
        public singleSongItem createFromParcel(Parcel parcel) {

            return  new singleSongItem(parcel);
        }

        @Override
        public singleSongItem[] newArray(int size) {

            return new singleSongItem[size];
        }
    };

    private singleSongItem (Parcel parcel) {

        this.songID = parcel.readLong();
        this.songTitle = parcel.readString();
        this.songArtistTitle = parcel.readString();
        this.songDuration = parcel.readInt();
        this.songIconId = parcel.readString();

    }

}
