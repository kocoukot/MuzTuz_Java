package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.commonFuncs.LevelsInfo;

import java.sql.Array;
import java.util.List;

public class PremiaChoose extends AppCompatActivity {


    private Integer imageSize = 0;
    private TextView textViewCoins, textViewStars;
    Integer width;
    private static final int CODEFORTUTORIAL = 1;
    private static final int CODEFORPREMIA = 2;
    private boolean gotMoneyForTutorial;
  //  private final int requaredAmount = 20;

    SharedPreferences preferencesProgress, preferencesPrizes;

    final String PREFERENCESProgress = "Preferences.progress";
    final String PREFERENCESPrizes = "Preferences.prizes";

    LinearLayout premiaSelectView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premia_choose);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        premiaSelectView = findViewById(R.id.premiaSelect);

        preferencesProgress = getSharedPreferences(PREFERENCESProgress, MODE_PRIVATE);
        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);

        textViewCoins = findViewById(R.id.premiaSelectCoins);
        textViewStars = findViewById(R.id.premiaSelectStars);

        premiaCreate();
        coinsStarsUpDate();
        gotMoneyForTutorial = preferencesPrizes.getBoolean("moneyForTutorial", false);

        if (!preferencesPrizes.getBoolean("firstMesInPremiaChoose", false)) {
            SharedPreferences.Editor editor = preferencesPrizes.edit();
            editor.putBoolean("firstMesInPremiaChoose", true);
            editor.apply();
            showInfo("Похоже это твой первый визит в игру МузТус! Рекомендуем сперва пройти небольшое обучение, чтобы разобраться что к чему. К тому же, если пройдешь обучение, получишь небольшой приятный стартовый бонус.");
        }
    }

    private void premiaCreate() {
        for (int i = 0; i < new LevelsInfo().premiasAmount; i++) {
            final ImageView premiaView = new ImageView(this);

            if (i == 0 || i == 1) {
                imageSize = width / 3;
            } else {
                imageSize = width / 2;
            }

            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(imageSize, imageSize);
            layout.setMargins(0, 50, 0, 50);
            premiaView.setId(i);

            if (i < 2) {
                premiaView.setImageResource(new LevelsInfo().premiaDisksList[i][levelsSolvedAmount(i)]);
                premiaView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onShowPremia(premiaView.getId());
                    }
                });
            } else {
                if (isNextOpened(i)) {
                    premiaView.setImageResource(new LevelsInfo().premiaDisksList[i][levelsSolvedAmount(i)]);
                    premiaView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onShowPremia(premiaView.getId());
                        }
                    });
                } else {
                    premiaView.setImageResource(new LevelsInfo().premiaDisksListClosed[i]);
                }
            }

            premiaSelectView.addView(premiaView, layout);
        }
    }


    private Integer levelsSolvedAmount(Integer premia) {
        Integer levelsSolvedAmount = 0;
        for (int i = 0; i < new LevelsInfo().premiaDisksList[premia].length; i++) {
            if (preferencesProgress.getInt("solved" + premia + i, 0) == 1) {
                levelsSolvedAmount += 1;
            }
        }
        return levelsSolvedAmount;
    }

    private boolean isNextOpened(Integer premia) {
        if ((((Double.valueOf(levelsSolvedAmount(premia - 1)) / Double.valueOf(new LevelsInfo().premiaDisksList[premia - 1].length)) * 100) >= new LevelsInfo().requaredAmount)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        coinsStarsUpDate();

        if (requestCode == CODEFORPREMIA) {
            if (resultCode == RESULT_CANCELED) {
                premiaSelectView.removeAllViews();
                premiaCreate();
            }
        }
         if (requestCode == CODEFORTUTORIAL) {
             if (resultCode == RESULT_CANCELED) {
                 premiaSelectView.removeAllViews();
                 premiaCreate();
                 System.out.println("test 1 ");
             } else {
                 if (!gotMoneyForTutorial) {
                     System.out.println("test 3");
                     gotMoneyForTutorial = true;

                     SharedPreferences.Editor coinsEditor = preferencesPrizes.edit();
                     coinsEditor.putInt("coins",preferencesPrizes.getInt("coins", 0) + 200);
                     coinsEditor.putBoolean("moneyForTutorial",true);
                     coinsEditor.apply();
                 }
                 premiaSelectView.removeAllViews();
                 premiaCreate();
                 coinsStarsUpDate();
                 System.out.println("test 2 ");

             }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onShowPremia(Integer premiaID) {
        Intent intent;
        if (premiaID > 0) {
            intent = new Intent(this, Premia.class);
            intent.putExtra("premiaIDChoosed", premiaID);
            startActivityForResult(intent, CODEFORPREMIA);
        } else {
            intent = new Intent(this, TutorialLvl.class);
            startActivityForResult(intent, CODEFORTUTORIAL);
        }
    }

    private void coinsStarsUpDate() {
        textViewCoins.setText(String.valueOf(preferencesPrizes.getInt("coins", 0)));
        textViewStars.setText(String.valueOf(preferencesPrizes.getInt("stars", 0)));
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


}
