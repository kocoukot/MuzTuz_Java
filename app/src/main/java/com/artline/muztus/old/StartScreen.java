package com.artline.muztus.old;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.artline.muztus.R;
import com.artline.muztus.audio.MusicPlayerService;

public class StartScreen extends AppCompatActivity {
    private final boolean musicOff = false;
    private final String PREFERENCESSounds = "Preferences.sounds";
    private ImageView imageView;
    private SharedPreferences preferencesSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        preferencesSounds = getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);

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
            MusicPlayerService.start(this);
        }
    }

}
