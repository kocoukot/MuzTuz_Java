package com.artline.muztus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.artline.muztus.audio.MusicPlayerService;

public class StartScreen extends AppCompatActivity {
    private boolean musicOff = false;

    private ImageView imageView;
    private final String PREFERENCESSounds = "Preferences.sounds";
    private SharedPreferences preferencesSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);

        imageView = findViewById(R.id.imagePlayButton);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartScreen.this.finish();
            }
        });

        soundsStatusSet();
    }

    private void soundsStatusSet() {
        if (preferencesSounds.getBoolean("musicPlay", true)) {
            MusicPlayerService.start(this, MusicPlayerService.MUSIC_MENU);
        }
    }

}
