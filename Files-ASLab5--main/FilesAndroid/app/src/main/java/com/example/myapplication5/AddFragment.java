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

    private static final int GALLERY_REQUEST_CODE = 123;
    private static final int AUDIO_REQUEST_CODE = 234;

    TextView title;
    ImageView imageView;
    Button addMusicBt;
    Button postBt;
    Button cancelBt;
    TextView songName;
    TextView description;
    Button deleteBt;
    TextView musicUriView;

    Uri imageUri = null;
    Uri musicUri = null;


    AddFragmentActivity addFrActivity;

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
//        postBt = view.findViewById(R.id.postBt);
//        cancelBt = view.findViewById(R.id.cancelBt);
        songName = view.findViewById(R.id.songName);
        // songName.setVisibility(View.GONE);
        deleteBt = view.findViewById(R.id.deleteBt);
        // deleteBt.setVisibility(View.GONE);

        imageView.setOnClickListener(v ->
        {
            //if (mainActivity.posted) {
            MusicPlayer.getInstance().play(getContext(), musicUri);
        });
        //deleteBt.setOnClickListener(v -> this.deleteFragment((Integer) view.getTag()));
        // deleteBt.setOnClickListener(v -> mainActivity.deleteFragment((Integer) view.getTag()));


        return view;
    }

}