package com.example.myapplication5;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment
{


    TextView title;
    ImageView imageView;
    Button addMusicBt;
    TextView songName;
    TextView description;
    Button deleteBt;

    Uri musicUri = null;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        title = view.findViewById(R.id.postTitle);
        description = view.findViewById(R.id.description);
        imageView = view.findViewById(R.id.addImage);
        addMusicBt = view.findViewById(R.id.addMusic);
        songName = view.findViewById(R.id.songName);
        deleteBt = view.findViewById(R.id.deleteBt);

        imageView.setOnClickListener(v ->
        {
            MusicPlayer.getInstance().play(getContext(), musicUri);
        });


        return view;
    }

}