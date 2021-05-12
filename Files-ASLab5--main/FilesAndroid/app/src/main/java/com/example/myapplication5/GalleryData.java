package com.example.myapplication5;

import java.util.ArrayList;
import java.util.List;

public class GalleryData
{
    private List<PostItem> data = new ArrayList<>();

    public GalleryData(List<PostDto> data)
    {
        if (data != null)
        {
            for (PostDto p : data)
            {
                this.data.add(new PostItem(p.getImageURI().toString(),
                        p.getTitle(), p.getDescription(), p.getSongURI().toString(), p.getSongName()));
            }
        }
    }

    public List<PostItem> getData()
    {
        return data;
    }

    public static class PostItem
    {
        private String imageURI;
        private String title;
        private String description;
        private String songURI;
        private String songName;

        public PostItem(String imageURI, String title, String description, String songURI, String songName)
        {
            this.imageURI = imageURI;
            this.title = title;
            this.description = description;
            this.songURI = songURI;
            this.songName = songName;
        }

        public String getImageURI()
        {
            return imageURI;
        }

        public String getTitle()
        {
            return title;
        }

        public String getDescription()
        {
            return description;
        }

        public String getSongURI()
        {
            return songURI;
        }

        public String getSongName()
        {
            return songName;
        }
    }
}
