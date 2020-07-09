package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.test.audio.MusicPlayerService;

public class Sozdateli extends AppCompatActivity {
    private boolean musicOff = false;
    private final String PREFERENCESSounds = "Preferences.sounds";
    private SharedPreferences preferencesSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sozdateli);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);

    }

    public void onCCRights(View view) {
        Uri uri = Uri.parse("https://yadi.sk/i/Gd7vNnOuFIgfQw");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (!musicOff){
            MusicPlayerService.pause();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!musicOff) {
            if (preferencesSounds.getBoolean("musicPlay", true)) {
                MusicPlayerService.resume(this);
            }
        }
        musicOff = false;
    }

}
