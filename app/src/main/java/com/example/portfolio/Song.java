package com.example.portfolio;

import android.graphics.Bitmap;

// CUSTOM CLASS TO HOLD THE METADATA INFO
public class Song {
    private Bitmap albumImage;
    private String title;
    private String artist;
    private String filePath;
    private int duration;

    public Song(String filePath, Bitmap albumImage, String title, String artist, int duration) {
        this.filePath = filePath;
        this.albumImage = albumImage;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public Bitmap getAlbumImage() {
        return albumImage;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getFilePath() {
        return filePath;
    }
    // USED IN SONGADAPTER
    public int getDuration() {
        return duration;
    }
}
