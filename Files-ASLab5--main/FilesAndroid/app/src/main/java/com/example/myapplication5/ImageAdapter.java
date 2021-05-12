package com.example.myapplication5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>
{

    private final List<com.example.myapplication5.PostDto> data = new ArrayList<>();

    Context context;

    public ImageAdapter(Context context)
    {
        this.context = context;
    }

    public void addItem(Uri image, String title, String description, Uri songUri, String songName)
    {
        com.example.myapplication5.PostDto postDto = new com.example.myapplication5.PostDto();
        postDto.setImageURI(image);
        postDto.setTitle(title);
        postDto.setDescription(description);
        postDto.setSongURI(songUri);
        postDto.setSongName(songName);
        data.add(postDto);
        com.example.myapplication5.FileStorage.write(this.context, new com.example.myapplication5.GalleryData(data));

        notifyDataSetChanged();
    }


    private final View.OnClickListener playMusicOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int position = (int) v.getTag();
            Uri musicUri = data.get(position).getSongURI();
            context.getContentResolver().takePersistableUriPermission(musicUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            com.example.myapplication5.MusicPlayer.getInstance().play(context, musicUri);
        }
    };

    private final View.OnClickListener openDescriptionClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            com.example.myapplication5.MusicPlayer.getInstance().pause();
            int position = (int) v.getTag();
            com.example.myapplication5.PostDto postDto = data.get(position);
            Uri imageUri = postDto.getImageURI();
            String title = postDto.getTitle();
            String description = postDto.getDescription();
            Uri musicUri = postDto.getSongURI();
            String songName = postDto.getSongName();
            Intent intent = new Intent(context, com.example.myapplication5.OpenPostActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            intent.putExtra("musicUri", musicUri.toString());
            intent.putExtra("songName", songName);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            context.startActivity(intent);
        }
    };

    private final View.OnClickListener deleteItemOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Integer position = (Integer) v.getTag();
            if (position != null)
            {
                com.example.myapplication5.PostDto postDto = data.get(position);
                if (postDto != null)
                {
                    data.remove(postDto);
                    com.example.myapplication5.FileStorage.write(com.example.myapplication5.ImageAdapter.this.context, new com.example.myapplication5.GalleryData(data));
                    com.example.myapplication5.MusicPlayer.getInstance().stop();
                    notifyDataSetChanged();
                }
            }
        }
    };

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_add, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        com.example.myapplication5.PostDto postDto = data.get(position);
        holder.bind(postDto);
        holder.imageView.setOnClickListener(playMusicOnClickListener);
        holder.imageView.setTag(position);
        holder.description.setOnClickListener(openDescriptionClickListener);
        holder.description.setTag(position);
        holder.deleteBt.setOnClickListener(deleteItemOnClickListener);
        holder.deleteBt.setTag(position);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView title;
        TextView description;
        TextView songName;
        Button deleteBt;

        public ImageViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.addImage);
            title = itemView.findViewById(R.id.postTitle);
            description = itemView.findViewById(R.id.description);
            songName = itemView.findViewById(R.id.songName);
            deleteBt = itemView.findViewById(R.id.deleteBt);
        }

        public void bind(com.example.myapplication5.PostDto postDto)
        {
            this.songName.setText("Song: " + postDto.getSongName());
            imageView.setImageURI(postDto.getImageURI());

            String description = postDto.getDescription();
            if (description.length() >= 40)
            {
                int lastWordIndex = 40;
                for (int i = 0; i <= 40; i++)
                {
                    if (description.charAt(i) == ' ')
                        lastWordIndex = i;
                }
                this.description.setText(description.substring(0, lastWordIndex) + "...");
            } else
            {
                this.description.setText(description);
            }

            String title = postDto.getTitle();
            if (title.length() >= 40)
            {
                int lastWordIndex = 40;
                for (int i = 0; i <= 40; i++)
                {
                    if (title.charAt(i) == ' ')
                        lastWordIndex = i;
                }
                this.title.setText(title.substring(0, lastWordIndex) + "...");
            }
            else
            {
                this.title.setText(title);
            }
            imageView.setVisibility(postDto.getImageURI() != null ? View.VISIBLE : View.GONE);
        }
    }
}
