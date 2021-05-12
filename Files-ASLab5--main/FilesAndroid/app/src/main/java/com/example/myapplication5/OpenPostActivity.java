package com.example.myapplication5;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OpenPostActivity extends AppCompatActivity
{

    TextView title;
    ImageView imageView;
    TextView description;
    MediaPlayer mp;

    Uri musicUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_post);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        findViewById(R.id.cancelButton).setOnClickListener(v -> cancel());

        title = findViewById(R.id.postTitle);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.addImage);

        setUp();

        prepareMediaPlayer();
    }

    void setUp()
    {
        Intent intent = getIntent();

        imageView = findViewById(R.id.imageView);
        imageView.setImageURI(Uri.parse(intent.getStringExtra("imageUri")));

        musicUri = Uri.parse(intent.getStringExtra("musicUri"));

        ((TextView) findViewById(R.id.addSongButton)).setText("Song: " + intent.getStringExtra("songName"));

        ((TextView) findViewById(R.id.titleTextView)).setText(intent.getStringExtra("title"));
        ((TextView) findViewById(R.id.titleTextView)).setMovementMethod(new ScrollingMovementMethod());

        description = findViewById(R.id.descriptionTextView);
        description.setText(intent.getStringExtra("description"));
        description.setMovementMethod(new ScrollingMovementMethod());

        description.setOnTouchListener((v, event) ->
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {

                try
                {
                    String url = com.example.myapplication5.Url.get
                            (description.getText().toString(),
                                    description.getOffsetForPosition(event.getX(), event.getY()));

                    if (mp.isPlaying())
                    {
                        mp.pause();
                    }

                    Intent intent1 = new Intent(com.example.myapplication5.OpenPostActivity.this, com.example.myapplication5.WebViewActivity.class);
                    intent1.putExtra("url", url);
                    startActivity(intent1);

                } catch (Exception e)
                {
                }
            }
            return false;
        });

        findViewById(R.id.cancelButton).setOnClickListener(v ->
        {
            mp.release();
            finish();
        });

        imageView.setOnClickListener(v -> play());
    }

    @Override
    public void onBackPressed()
    {
        mp.release();
        super.onBackPressed();
    }

    void prepareMediaPlayer()
    {
        mp = new MediaPlayer();
        mp = MediaPlayer.create(this, musicUri);
    }

    void play()
    {
        if (mp.isPlaying())
        {
            mp.pause();
        } else
        {
            mp.start();
        }
    }

    void cancel()
    {
        finish();
    }
}
