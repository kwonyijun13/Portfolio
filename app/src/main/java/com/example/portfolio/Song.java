package com.example.portfolio;

import android.graphics.Bitmap;

// CUSTOM CLASS TO HOLD THE METADATA INFO
public class Song {
    private Bitmap albumImage; // Base64 encoded string of the album image
    private String title; // Song title
    private String artist; // Artist name

    public Song(String id, Bitmap albumImage, String title, String artist) {
        this.albumImage = albumImage;
        this.title = title;
        this.artist = artist;
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
}
