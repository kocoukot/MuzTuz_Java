package com.artline.muztus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.artline.muztus.audio.MusicPlayerService;

public class Sozdateli extends AppCompatActivity {
    private boolean musicOff = false;
    private final String PREFERENCESSounds = "Preferences.sounds";
    private SharedPreferences preferencesSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sozdateli);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);
        Intent intent = getIntent();
        musicOff = intent.getBooleanExtra("musicOff", false);

    }

    public void onCCRights(View view) {
        Uri uri = Uri.parse("https://yadi.sk/i/Gd7vNnOuFIgfQw");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        musicOff = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!musicOff){
            MusicPlayerService.pause();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                musicOff = false;
                return false;
            case KeyEvent.KEYCODE_HOME:
                musicOff = false;
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!musicOff){
            if (preferencesSounds.getBoolean("musicPlay", true)) {
                MusicPlayerService.resume(this);
            }
        }
        musicOff = false;
    }

}
