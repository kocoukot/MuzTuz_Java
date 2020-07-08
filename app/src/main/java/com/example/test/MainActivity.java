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
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private TextView textViewCoins;
    private TextView textViewStars;

    private SharedPreferences preferencesProgress;
    private SharedPreferences preferencesPrizes;

    private final String PREFERENCESProgress = "Preferences.progress";
    private final String PREFERENCESPrizes = "Preferences.prizes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);

        textViewCoins = findViewById(R.id.menuCoins);
        textViewStars = findViewById(R.id.menuStars);
        coinsStarsUpDate();
        if (!preferencesPrizes.getBoolean("firstMesInMenu", false)) {
            SharedPreferences.Editor editor = preferencesPrizes.edit();
            editor.putBoolean("firstMesInMenu", true);
            editor.apply();
            showInfo();
        }
    }

    public void onStartPlay(View view) {
        intent = new Intent(this, PremiaChoose.class);

        startActivityForResult(intent,1);
       // startActivity(intent);
    }

    public void onStatistic(View view) {
        intent = new Intent(this, Statistika.class);
        startActivity(intent);
    }

    public void onTest(View view) {
        intent = new Intent(this, Shop.class);
        startActivityForResult(intent,1);
        // startActivity(intent);

    }

    public void onReset(View view) {
        showDialogMasseg();
    }

    public void onSozdateli(View view) {
        intent = new Intent(this, Sozdateli.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        coinsStarsUpDate();
    }


    private void coinsStarsUpDate() {
        textViewCoins.setText(String.valueOf(preferencesPrizes.getInt("coins", 0)));
        textViewStars.setText(String.valueOf(preferencesPrizes.getInt("stars", 0)));
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
}
