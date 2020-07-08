package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

public class StartScreen extends AppCompatActivity {

    private VideoView videoView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        videoView = findViewById(R.id.videoView);
        String pathToVideo = "android.resource://com.example.test/" + R.raw.intro_video;
        videoView.setVideoPath(pathToVideo);

        videoView.start();
        imageView = findViewById(R.id.imagePlayButton);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartScreen.this.finish();
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVisibility(View.INVISIBLE);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVisibility(View.INVISIBLE);

            }
        });
    }
}
