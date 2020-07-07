package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.test.commonFuncs.LevelsInfo;

public class Statistika extends AppCompatActivity {

    TextView sumDuration, levelSolved, helpsUsed, fastestText, lowestText, statisticHead;
    ImageView fastestImage, lowestImage;
    ScrollView statisticScroll;

    SharedPreferences preferencesProgress, preferencesPrizes;

    final String PREFERENCESProgress = "Preferences.progress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        long[] list = sumDuration();
        if (list[0] > 0) {
            sumDuration.setText(sumDuration.getText() + " " + secondsToHoursMinutesSeconds(list[0]));
            levelSolved.setText(levelSolved.getText() + " " + helpsUsedAmount()[0] + " / " + new LevelsInfo().getLevelsAmount());
            helpsUsed.setText(helpsUsed.getText() + " " + helpsUsedAmount()[1]);

            fastestText.setText(fastestText.getText() + " " + Double.valueOf(list[1]) / 1000 + " сек.");
            lowestText.setText(lowestText.getText() + " " + Double.valueOf(list[2]) / 1000 + " сек.");

            fastestImage.setImageResource(new LevelsInfo().premiaImagesList[(int) list[3]][(int) list[4]]);
            lowestImage.setImageResource(new LevelsInfo().premiaImagesList[(int) list[5]][(int) list[6]]);
        } else {
            statisticScroll.setVisibility(View.INVISIBLE);
            statisticHead.setText("К сожалению для формирвоания статистики мало данных.");
        }

    }


    private int[] helpsUsedAmount() {
        // пройденно уровней , использованно подсказок, быстрый ответ картинка, долгий ответ картинка
        int[] list = new int [2];

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
        long[] list = new long [7];
        list[0] = 0;
        list[1] = 999999;
        list[2] = 0;
        for (int p = 1; p < new LevelsInfo().premiaImagesList.length-1; p++) {
            for (int l = 0; l < new LevelsInfo().premiaImagesList[p].length; l++) {
                long timespend = preferencesProgress.getLong("lvlDuration" + p + l, 0);
                list[0] += preferencesProgress.getLong("lvlDuration" + p + l, 0);
                if (timespend < list[1] & timespend != 0){
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

    private String secondsToHoursMinutesSeconds (long seconds) {
        seconds = seconds/1000;
        return ((seconds % 3600) / 60) + " мин. " + ((seconds % 3600) % 60) + " сек.";
    }

}
