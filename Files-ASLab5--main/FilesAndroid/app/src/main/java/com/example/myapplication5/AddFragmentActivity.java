package com.example.myapplication5;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddFragmentActivity extends AppCompatActivity
{

    private static int ADD_IMAGE_REQUEST_CODE = 101;

    EditText title;
    EditText description;
    ImageView imageView;
    Button addSongButton;

    Uri imageUri = null;
    Uri songUri = null;

    private static final int GALLERY_REQUEST = 102;
    private static final int MUSIC_REQUEST = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fragment);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        findViewById(R.id.cancelButton).setOnClickListener(v -> cancel());

        findViewById(R.id.confirmButton).setOnClickListener(v -> confirm());

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> addImage());

        title = findViewById(R.id.titleTextView);

        description = findViewById(R.id.descriptionTextView);

        addSongButton = findViewById(R.id.addSongButton);
        addSongButton.setOnClickListener(v -> addSong());
    }

    void addSong()
    {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Pick an audio: "), MUSIC_REQUEST);
    }

    void addImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Pick an image: "), GALLERY_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data != null)
        {
            imageUri = data.getData();

            imageView.setImageURI(imageUri);
        } else if (requestCode == MUSIC_REQUEST && resultCode == Activity.RESULT_OK && data != null)
        {
            songUri = data.getData();

            addSongButton.setText(getFileName(songUri));
        }
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

    void cancel()
    {
        finish();
    }

    void confirm()
    {
        if (title.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Enter the title!", Toast.LENGTH_SHORT).show();
            return;
        } else if (imageUri == null)
        {
            Toast.makeText(this, "Pick an image!", Toast.LENGTH_SHORT).show();
            return;
        } else if (songUri == null)
        {
            Toast.makeText(this, "Pick an audio!", Toast.LENGTH_SHORT).show();
            return;
        } else if (description.getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Enter the description!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("imageUri", imageUri.toString());
        intent.putExtra("musicUri", songUri.toString());
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("description", description.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}