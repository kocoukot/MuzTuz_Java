package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.audio.MusicPlayerService;
import com.example.test.audio.SoundsPlayerService;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private TextView textViewCoins;
    private TextView textViewStars;


    private ImageView musicButton, soundsButton;


    private SharedPreferences preferencesProgress, preferencesPrizes;

    private final String PREFERENCESProgress = "Preferences.progress";
    private final String PREFERENCESPrizes = "Preferences.prizes";
    private final String PREFERENCESSounds = "Preferences.sounds";
    private SharedPreferences preferencesSounds;

    private boolean musicOff = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);

        textViewCoins = findViewById(R.id.menuCoins);
        textViewStars = findViewById(R.id.menuStars);
        musicButton = findViewById(R.id.menuMusic);
        soundsButton = findViewById(R.id.menuSound);



        soundsStatusSet();
        coinsStarsUpDate();
        if (!preferencesPrizes.getBoolean("firstMesInMenu", false)) {
            SharedPreferences.Editor editor = preferencesPrizes.edit();
            editor.putBoolean("firstMesInMenu", true);
            editor.apply();
            showInfo();
        }
        Intent intentSC = new Intent(this, StartScreen.class);
        startActivity(intentSC);
    }

    public void onStartPlay(View view) {
        intent = new Intent(this, PremiaChoose.class);
        startActivityForResult(intent,1);
        musicOff = true;
    }

    public void onStatistic(View view) {
        intent = new Intent(this, Statistika.class);
        startActivityForResult(intent,1);
        musicOff = true;
    }

    public void onTest(View view) {
        intent = new Intent(this, Shop.class);
        startActivityForResult(intent,1);
        musicOff = true;
    }

    public void onReset(View view) {
        showDialogMasseg();
    }

    public void onSozdateli(View view) {
        intent = new Intent(this, Sozdateli.class);
        startActivityForResult(intent,1);
        musicOff = true;

    }

    private void soundsStatusSet(){
        if (preferencesSounds.getBoolean("musicPlay", true)){
            musicButton.setImageResource(R.drawable.buton_music_on);
            MusicPlayerService.start(this,MusicPlayerService.MUSIC_MENU);
        } else {
            musicButton.setImageResource(R.drawable.buton_music_off);
        }

        if (preferencesSounds.getBoolean("soundsPlay", true)){
            soundsButton.setImageResource(R.drawable.buton_sound_on);
        } else {
            soundsButton.setImageResource(R.drawable.buton_sound_off);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        coinsStarsUpDate();
        System.out.println(musicOff);

        //musicOff = false;
    }


    private void coinsStarsUpDate() {
        textViewCoins.setText(String.valueOf(preferencesPrizes.getInt("coins", 0)));
        textViewStars.setText(String.valueOf(preferencesPrizes.getInt("stars", 0)));

        if (preferencesSounds.getBoolean("musicPlay", true)){
            musicButton.setImageResource(R.drawable.buton_music_on);
        } else {
            musicButton.setImageResource(R.drawable.buton_music_off);
        }

        if (preferencesSounds.getBoolean("soundsPlay", true)) {
            soundsButton.setImageResource(R.drawable.buton_sound_on);
        } else {
            soundsButton.setImageResource(R.drawable.buton_sound_off);
        }
    }

    private void showDialogMasseg() {
        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.help);
        TextView text = builder.findViewById(R.id.textInform);
        final Button buttonYes = builder.findViewById(R.id.buttonHelpYes);
        final Button buttonNo = builder.findViewById(R.id.buttonHelpNo);
        text.setText("Вы уверены, что хотите сбросить прогресс игры?");
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f);
        layout.setMargins(50, 50, 50, 50);
        text.setLayoutParams(layout);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetProgress();
                builder.cancel();
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        builder.show();
    }

    private void resetProgress() {
        System.out.println(preferencesSounds.getBoolean("soundsPlay", true));
        SoundsPlayerService.start(this, SoundsPlayerService.SOUND_RESET_ALL,preferencesSounds.getBoolean("soundsPlay", true));

        SharedPreferences.Editor editor = preferencesPrizes.edit();
        editor.putInt("stars", 0);
        editor.apply();

        SharedPreferences.Editor progressEditor = preferencesProgress.edit();
        progressEditor.clear();
        progressEditor.apply();
        coinsStarsUpDate();
    }


    private void showInfo() {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.inform);

        TextView text = builder.findViewById(R.id.textInform);
        final Button button = builder.findViewById(R.id.buttonInformOK);
        text.setText("Вся информация, представленная в приложении, за исключением информации, имеющей ссылку на конкретный источник, является художественным вымыслом и не имеет отношения к реальным лицам и событиям. Автор не несет ответственности за случайным совпадения с реальными лицами и событиями.");

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.67f);
        layout.setMargins(50, 50, 50, 50);
        text.setLayoutParams(layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        builder.show();
    }

    public void onClickVK(View view) {
        social("https://vk.com/kocou_kot");
    }

    public void onClickInst(View view) {
        social("https://www.instagram.com/alexzhegulov/");

    }

    private void social(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void onMelody(View view) {
        SharedPreferences.Editor editor = preferencesSounds.edit();

        if (preferencesSounds.getBoolean("musicPlay", true)){

            musicButton.setImageResource(R.drawable.buton_music_off);
            MusicPlayerService.pause();
            editor.putBoolean("musicPlay", false);

        } else {
            musicButton.setImageResource(R.drawable.buton_music_on);
            MusicPlayerService.resume(this);
            editor.putBoolean("musicPlay", true);
        }
        editor.apply();
    }

    public void onSounds(View view) {
        SharedPreferences.Editor editor = preferencesSounds.edit();

        if (preferencesSounds.getBoolean("soundsPlay", true)){
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_OFF_MUSIC,true);

            soundsButton.setImageResource(R.drawable.buton_sound_off);
           // MusicPlayerService.pause();
            editor.putBoolean("soundsPlay", false);

        } else {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_ON_MUSIC,true);

            soundsButton.setImageResource(R.drawable.buton_sound_on);
          //  MusicPlayerService.resume(this);
            editor.putBoolean("soundsPlay", true);
        }
        editor.apply();
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
        musicOff = false;
        if (!musicOff) {
            if (preferencesSounds.getBoolean("musicPlay", true)) {
                MusicPlayerService.resume(this);
            }
        }

    }


}
