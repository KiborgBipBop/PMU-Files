package com.example.myapplication5;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MusicPlayer
{
    private static com.example.myapplication5.MusicPlayer instance;

    Uri musicUri;
    MediaPlayer mp;
    boolean paused = false;

    public MusicPlayer()
    {
        mp = new MediaPlayer();
        musicUri = null;
    }

    public static com.example.myapplication5.MusicPlayer getInstance()
    {
        if (instance == null)
            instance = new com.example.myapplication5.MusicPlayer();
        return instance;
    }


    public boolean play(Context context, Uri uri)
    {
        if (uri == null)
            return false;

        if (uri == musicUri)
        {
            if (!paused)
            {
                mp.pause();
                paused = true;
                return false;
            } else
            {
                mp.start();
                paused = false;
                return true;
            }
        } else
        {
            if (mp.isPlaying())
                mp.stop();
            try
            {
                musicUri = uri;
                mp = MediaPlayer.create(context, musicUri);

                mp.start();
                mp.setLooping(true);
                return true;
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            } catch (SecurityException e)
            {
                e.printStackTrace();
            } catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void stop()
    {
        if (mp.isPlaying()) mp.stop();
    }

    public void pause()
    {
        if (mp.isPlaying()) mp.pause();
    }
}