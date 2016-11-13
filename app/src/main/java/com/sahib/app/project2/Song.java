package com.sahib.app.project2;

/**
 * Created by Sahib on 2/18/2016.
 */
public class Song {

    private int iconID;
    private String title;
    private String artist;

    public Song(String title, String artist, int iconID) {
        this.iconID = iconID;
        this.title = title;
        this.artist = artist;
    }

    public int getIconID() {
        return iconID;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

}
