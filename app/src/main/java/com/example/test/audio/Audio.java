package com.example.test.audio;

import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import com.example.test.R;


public class Audio extends AppCompatActivity {

    public static Audio audio = new Audio();

    public void playSound(){
        MediaPlayer soundPlayer = MediaPlayer.create(this, R.raw.common);
        soundPlayer.start();
    }
}
