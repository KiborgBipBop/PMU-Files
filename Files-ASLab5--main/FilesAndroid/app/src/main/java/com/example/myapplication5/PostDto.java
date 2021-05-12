package com.example.myapplication5;

import android.net.Uri;

public class PostDto
{

    private Uri imageURI;
    private String title;
    private String description;
    private Uri songURI;
    private String songName;

    public Uri getImageURI()
    {
        return imageURI;
    }

    public void setImageURI(Uri imageURI)
    {
        this.imageURI = imageURI;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Uri getSongURI()
    {
        return songURI;
    }

    public void setSongURI(Uri songURI)
    {
        this.songURI = songURI;
    }

    public String getSongName()
    {
        return songName;
    }

    public void setSongName(String songName)
    {
        this.songName = songName;
    }
}
