package com.artline.muztus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.artline.muztus.audio.MusicPlayerService;
import com.artline.muztus.commonFuncs.LevelsInfo;

public class Statistika extends AppCompatActivity {

    private TextView sumDuration;
    private TextView levelSolved;
    private TextView helpsUsed;
    private TextView fastestText;
    private TextView lowestText;
    private TextView statisticHead;
    private ImageView fastestImage;
    private ImageView lowestImage;
    private ScrollView statisticScroll;
    private boolean musicOff = false;

    private SharedPreferences preferencesProgress;
    SharedPreferences preferencesPrizes;

    private final String PREFERENCESProgress = "Preferences.progress";
    private final String PREFERENCESSounds = "Preferences.sounds";
    private SharedPreferences preferencesSounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        musicOff = intent.getBooleanExtra("musicOff", false);


        setContentView(R.layout.activity_statistika);
        sumDuration = findViewById(R.id.sumDuration);
        levelSolved = findViewById(R.id.levelSolved);
        helpsUsed = findViewById(R.id.helpsUsed);
        fastestText = findViewById(R.id.fastestText);
        lowestText = findViewById(R.id.lowestText);
        fastestImage = findViewById(R.id.fastestImage);
        lowestImage = findViewById(R.id.lowestImage);
        statisticHead = findViewById(R.id.statisticHead);
        statisticScroll = findViewById(R.id.statisticScroll);

        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);

        long[] list = sumDuration();
        if (list[0] > 0) {
            sumDuration.setText(sumDuration.getText() + " " + secondsToHoursMinutesSeconds(list[0]));
            levelSolved.setText(levelSolved.getText() + " " + helpsUsedAmount()[0] + " / " + new LevelsInfo().getLevelsAmount());
            helpsUsed.setText(helpsUsed.getText() + " " + helpsUsedAmount()[1]);

            fastestText.setText(fastestText.getText() + " " + (double) list[1] / 1000 + " сек.");
            lowestText.setText(lowestText.getText() + " " + (double) list[2] / 1000 + " сек.");

            fastestImage.setImageResource(new LevelsInfo().premiaImagesList[(int) list[3]][(int) list[4]]);
            lowestImage.setImageResource(new LevelsInfo().premiaImagesList[(int) list[5]][(int) list[6]]);
        } else {
            statisticScroll.setVisibility(View.INVISIBLE);
            statisticHead.setText("К сожалению для формирвоания статистики мало данных.");
        }

    }


    private int[] helpsUsedAmount() {
        // пройденно уровней , использованно подсказок, быстрый ответ картинка, долгий ответ картинка
        int[] list = new int[2];

        for (int p = 1; p < new LevelsInfo().premiaImagesList.length; p++) {
            for (int l = 0; l < new LevelsInfo().premiaImagesList[p].length; l++) {
                for (int h = 0; h < 4; h++) {
                    list[1] += preferencesProgress.getInt("helpUsed" + p + l + h, 0);
                }
                list[0] += preferencesProgress.getInt("solved" + p + l, 0);
            }
        }
        return list;
    }

    private long[] sumDuration() {
        // пройденно уровней , использованно подсказок, быстрый ответ картинка, долгий ответ картинка
        long[] list = new long[7];
        list[0] = 0;
        list[1] = 999999;
        list[2] = 0;
        for (int p = 1; p < new LevelsInfo().premiaImagesList.length - 1; p++) {
            for (int l = 0; l < new LevelsInfo().premiaImagesList[p].length; l++) {
                long timespend = preferencesProgress.getLong("lvlDuration" + p + l, 0);
                list[0] += preferencesProgress.getLong("lvlDuration" + p + l, 0);
                if (timespend < list[1] & timespend != 0) {
                    list[1] = timespend;
                    list[3] = p;
                    list[4] = l;
                }
                if (timespend > list[2]) {
                    list[2] = timespend;
                    list[5] = p;
                    list[6] = l;
                }
            }
        }
        return list;
    }

    private String secondsToHoursMinutesSeconds(long seconds) {
        seconds = seconds / 1000;
        return ((seconds % 3600) / 60) + " мин. " + ((seconds % 3600) % 60) + " сек.";
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
