package com.example.shoaib.gomusic;

/**
 * Created by Shoaib on 1/4/2016.
 */
public class soundCloudSingleAudioItem {


    public String sharedType;
    public boolean whetherAudioStreamable;
    public String artWorkUrl;
    public String audioStreamingUrL;
    public String audioTitle;
    public long audioDuration;


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





}
