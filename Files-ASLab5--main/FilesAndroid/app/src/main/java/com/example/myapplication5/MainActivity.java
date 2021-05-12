package com.example.myapplication5;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
{

    boolean posted = true;

    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    LinearLayoutManager linearLayoutManager;

    private static final int ADD_IMAGE_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        initRecyclerView();

        signInMessage();

        GalleryData data = com.example.myapplication5.FileStorage.read(this.getApplicationContext());
        if (data != null)
        {
            for (GalleryData.PostItem postDto : data.getData())
            {
                imageAdapter.addItem(Uri.parse(postDto.getImageURI()), postDto.getTitle(), postDto.getDescription(),
                        Uri.parse(postDto.getSongURI()), postDto.getSongName());
            }
        }
    }

    void initRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageAdapter = new ImageAdapter(this);
        recyclerView.setAdapter(imageAdapter);
    }

    private void signInMessage()
    {
        Toast.makeText(this, "Hello, " + com.example.myapplication5.Controller.getInstance().getFullName() + "!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("");
            builder.setMessage("Do you want to Sign Out from your account?");
            builder.setPositiveButton("Yes", (dialog, which) ->
            {
                Intent logInIntent = new Intent(com.example.myapplication5.MainActivity.this, com.example.myapplication5.SignInActivity.class);
                startActivity(logInIntent);
                MusicPlayer.getInstance().stop();
                finish();
            });
            builder.setNegativeButton("No", (dialog, which) ->
            {
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openFragment(View view)
    {
        MusicPlayer.getInstance().pause();

        Intent intent = new Intent(com.example.myapplication5.MainActivity.this, com.example.myapplication5.AddFragmentActivity.class);
        startActivityForResult(intent, ADD_IMAGE_REQUEST_CODE);
    }

    public String getFileName(Uri uri)
    {
        String result = null;
        if (uri.getScheme().equals("content"))
        {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            try
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally
            {
                cursor.close();
            }
        }
        if (result == null)
        {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1)
            {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Uri imageUri = Uri.parse(extras.getString("imageUri"));
            String title = extras.getString("title");
            String description = extras.getString("description");
            Uri musicUri = Uri.parse(extras.getString("musicUri"));
            getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(musicUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

            imageAdapter.addItem(imageUri, title, description, musicUri, getFileName(musicUri));

            linearLayoutManager.scrollToPositionWithOffset(imageAdapter.getItemCount() - 1, 0);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    public void setPosted(boolean posted)
    {
        this.posted = posted;
    }

}