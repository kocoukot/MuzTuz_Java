package com.example.test;

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
import android.widget.VideoView;
import android.media.MediaPlayer;

import com.example.test.audio.Audio;
import com.example.test.commonFuncs.CommonFunc;

public class MainActivity extends AppCompatActivity {

    Button button;
    Intent intent;

    TextView textViewCoins, textViewStars;

    SharedPreferences preferencesProgress, preferencesPrizes;
    final String PREFERENCES = "testMuzTus";

    final String PREFERENCESProgress = "Preferences.progress";
    final String PREFERENCESPrizes = "Preferences.prizes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);

        button = findViewById(R.id.buttonPlay);
        textViewCoins = findViewById(R.id.menuCoins);
        textViewStars = findViewById(R.id.menuStars);
        coinsStarsUpDate();
        if (!preferencesPrizes.getBoolean("firstMesInMenu", false)) {
            SharedPreferences.Editor editor = preferencesPrizes.edit();
            editor.putBoolean("firstMesInMenu", true);
            editor.apply();
            showInfo("Вся информация, представленная в приложении, за исключением информации, имеющей ссылку на конкретный источник, является художественным вымыслом и не имеет отношения к реальным лицам и событиям. Автор не несет ответственности за случайным совпадения с реальными лицами и событиями.");
        }
    }

    public void onStartPlay(View view) {
        intent = new Intent(this, PremiaChoose.class);
        startActivity(intent);
    }

    public void onStatistic(View view) {
        intent = new Intent(this, Statistika.class);
        startActivity(intent);
    }

    public void onTest(View view) {
        SharedPreferences.Editor coinsEditor = preferencesPrizes.edit();
        coinsEditor.putInt("coins", preferencesPrizes.getInt("coins", 0) + 999);
        coinsEditor.apply();
        coinsStarsUpDate();
    }

    public void onReset(View view) {
        showDialogMasseg("Вы уверены, что хотите сбросить прогресс игры?");
    }

    public void onSozdateli(View view) {
        intent = new Intent(this, Sozdateli.class);
        startActivity(intent);
    }


    private void coinsStarsUpDate() {
        textViewCoins.setText(String.valueOf(preferencesPrizes.getInt("coins", 0)));
        textViewStars.setText(String.valueOf(preferencesPrizes.getInt("stars", 0)));
    }


    private void showDialogMasseg(final String message) {
        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.help);
        TextView text = builder.findViewById(R.id.textInform);
        final Button buttonYes = builder.findViewById(R.id.buttonHelpYes);
        final Button buttonNo = builder.findViewById(R.id.buttonHelpNo);
        text.setText(message);
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
        SharedPreferences.Editor editor = preferencesPrizes.edit();
        //editor.putInt("coins", 0);
        editor.putInt("stars", 0);
        editor.apply();

        SharedPreferences.Editor progressEditor = preferencesProgress.edit();
        progressEditor.clear();
        progressEditor.apply();
        coinsStarsUpDate();
    }


    private void showInfo(String message) {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.inform);

        TextView text = builder.findViewById(R.id.textInform);
        final Button button = builder.findViewById(R.id.buttonInformOK);
        text.setText(message);

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
}
