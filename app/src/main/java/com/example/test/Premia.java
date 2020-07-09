package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.audio.MusicPlayerService;
import com.example.test.audio.SoundsPlayerService;
import com.example.test.commonFuncs.GridAdapter;
import com.example.test.commonFuncs.LevelsInfo;

public class Premia extends AppCompatActivity {

    private static final int CODEFORLVL = 3;
    private TextView textViewCoins;
    private TextView textViewStars;
    private int selectedLvl;

    private SharedPreferences preferencesProgress;
    private SharedPreferences preferencesPrizes;

    private SharedPreferences preferencesSounds;
    private final String PREFERENCESSounds = "Preferences.sounds";
    private final String PREFERENCESProgress = "Preferences.progress";
    private final String PREFERENCESPrizes = "Preferences.prizes";

    private Integer[] levelsSolvedList;
    private boolean nextPremiaIsOpened;

    private int premiaID;
    private GridView gridView;
    private GridAdapter adapter;
    private ImageView musicButton, soundsButton;
    private boolean musicOff = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_premiya);
        Intent premiaIntent = getIntent();


        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);


        textViewCoins = findViewById(R.id.premiaCoins);
        textViewStars = findViewById(R.id.premiaStars);
        musicButton = findViewById(R.id.levelsMusic);
        soundsButton = findViewById(R.id.levelsSounds);


        premiaID = premiaIntent.getIntExtra("premiaIDChoosed", 0);
        Integer[] levelsList = new LevelsInfo().premiaImagesList[premiaIntent.getIntExtra("premiaIDChoosed", 0)];
        levelsSolvedList = new Integer[levelsList.length];
        for (int i = 0; i < levelsList.length; i++) {
            levelsSolvedList[i] = preferencesProgress.getInt("solved" + premiaID + i, 0);
        }

        gridView = findViewById(R.id.gridView);

        adapter = new GridAdapter(Premia.this, levelsList, levelsSolvedList);

        setGrid();
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startLvl(position);
//            }
//        });
        coinsStarsUpDate();
        nextPremiaIsOpened = hasOpenedNextPremia();
    }

    private void setGrid(){
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startLvl(position);
            }
        });
    }


    private void startLvl(Integer position) {
        selectedLvl = position;
        Intent intent = new Intent(getApplicationContext(), Level.class);
        intent.putExtra("position", position);
        intent.putExtra("premiaIDtoLVL", premiaID);
        startActivityForResult(intent, CODEFORLVL);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODEFORLVL) {
            coinsStarsUpDate();
            levelsSolvedList[selectedLvl] = preferencesProgress.getInt("solved" + premiaID + selectedLvl, 0);
            adapter.notifyDataSetChanged();
            if (hasOpenedNextPremia() & !nextPremiaIsOpened) {
                nextPremiaIsOpened = hasOpenedNextPremia();
                showInfo();
            }
        }
    }

    private boolean hasOpenedNextPremia() {
        return (((Double.valueOf(levelsSolvedAmount(premiaID)) / (double) new LevelsInfo().premiaDisksList[premiaID].length) * 100) >= new LevelsInfo().requaredAmount) && premiaID < 6;
    }

    private Integer levelsSolvedAmount(Integer premia) {
        int levelsSolvedAmount = 0;
        for (int i = 0; i < new LevelsInfo().premiaDisksList[premia].length; i++) {
            if (preferencesProgress.getInt("solved" + premia + i, 0) == 1) {
                levelsSolvedAmount += 1;
            }
        }
        return levelsSolvedAmount;
    }

    private void showInfo() {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.inform);

        TextView text = builder.findViewById(R.id.textInform);
        final Button button = builder.findViewById(R.id.buttonInformOK);
        text.setText("Поздравляем с открытием следующей премии!");

        SoundsPlayerService.start(Premia.this, SoundsPlayerService.SOUND_OPEN_PREMIA,preferencesSounds.getBoolean("soundsPlay", true));

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!musicOff) {
//            if (preferencesSounds.getBoolean("musicPlay", true)) {
//                MusicPlayerService.resume(this);
//            }
//        }
//        musicOff = false;
//    }
}
