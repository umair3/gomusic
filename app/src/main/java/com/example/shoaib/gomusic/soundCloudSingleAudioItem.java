package com.example.shoaib.gomusic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shoaib on 1/4/2016.
 */
public class soundCloudSingleAudioItem implements Parcelable {


    public String sharedType;
    public boolean whetherAudioStreamable;
    public String artWorkUrl;
    public String audioStreamingUrL;
    public String audioTitle;
    public long audioDuration;

    public soundCloudSingleAudioItem(){


    }

    public soundCloudSingleAudioItem(soundCloudSingleAudioItem mItem){
        this.sharedType =mItem.sharedType;
        this.whetherAudioStreamable = mItem.whetherAudioStreamable;
        this.artWorkUrl= mItem.artWorkUrl;
        this.audioStreamingUrL = mItem.audioStreamingUrL;
        this.audioTitle = mItem.audioTitle;
        this.audioDuration = mItem.audioDuration;

    }
    public boolean isWhetherAudioStreamable() {
        return whetherAudioStreamable;
    }

    public void setWhetherAudioStreamable(boolean whetherAudioStreamable) {
        this.whetherAudioStreamable = whetherAudioStreamable;
    }

    public String getSharedType() {
        return sharedType;
    }

    public void setSharedType(String mSharedType) {
        this.sharedType = mSharedType;
    }

    public String getArtWorkUrl() {
        return artWorkUrl;
    }

    public void setArtWorkUrl(String artWorkUrl) {
        this.artWorkUrl = artWorkUrl;
    }

    public String getAudioStreamingUrL() {
        return audioStreamingUrL;
    }

    public void setAudioStreamingUrL(String audioStreamingUrL) {
        this.audioStreamingUrL = audioStreamingUrL;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public long getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(long audioDuration) {
        this.audioDuration = audioDuration;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sharedType);
        parcel.writeByte((byte) (whetherAudioStreamable ? 1 : 0));
        parcel.writeString(artWorkUrl);
        parcel.writeString(audioStreamingUrL);
        parcel.writeString(audioTitle);
        parcel.writeLong(audioDuration);
    }

    public static final Creator<soundCloudSingleAudioItem> CREATOR = new Creator<soundCloudSingleAudioItem>() {
        @Override
        public soundCloudSingleAudioItem createFromParcel(Parcel in) {
            return new soundCloudSingleAudioItem(in);
        }

        @Override
        public soundCloudSingleAudioItem[] newArray(int size) {
            return new soundCloudSingleAudioItem[size];
        }
    };

    protected soundCloudSingleAudioItem(Parcel in) {
        sharedType = in.readString();
        whetherAudioStreamable = in.readByte() != 0;
        artWorkUrl = in.readString();
        audioStreamingUrL = in.readString();
        audioTitle = in.readString();
        audioDuration = in.readLong();
    }


}
