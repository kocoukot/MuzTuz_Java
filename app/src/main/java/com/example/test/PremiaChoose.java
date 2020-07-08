package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.example.test.commonFuncs.LevelsInfo;


public class PremiaChoose extends AppCompatActivity {


    private TextView textViewCoins, textViewStars;
    private Integer width;
    private static final int CODEFORTUTORIAL = 1;
    private static final int CODEFORPREMIA = 2;
    private boolean gotMoneyForTutorial;
    //  private final int requaredAmount = 20;

    private SharedPreferences preferencesProgress;
    private SharedPreferences preferencesPrizes;

    private final String PREFERENCESProgress = "Preferences.progress";
    private final String PREFERENCESPrizes = "Preferences.prizes";

    private LinearLayout premiaSelectView;

    private InterstitialAd mInterstitialAd;
//test
    String AdID = "ca-app-pub-3940256099942544/1033173712";
//norm
//    String AdID = "ca-app-pub-8364051315582457/2833852870";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premia_choose);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
            showInfo();
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    private void premiaCreate() {
        for (int i = 0; i < new LevelsInfo().premiasAmount; i++) {
            final ImageView premiaView = new ImageView(this);

            int imageSize;
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
        int levelsSolvedAmount = 0;
        for (int i = 0; i < new LevelsInfo().premiaDisksList[premia].length; i++) {
            if (preferencesProgress.getInt("solved" + premia + i, 0) == 1) {
                levelsSolvedAmount += 1;
            }
        }
        return levelsSolvedAmount;
    }

    private boolean isNextOpened(Integer premia) {
        return (((Double.valueOf(levelsSolvedAmount(premia - 1)) / (double) new LevelsInfo().premiaDisksList[premia - 1].length) * 100) >= new LevelsInfo().requaredAmount);
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        coinsStarsUpDate();

        if (requestCode == CODEFORPREMIA) {
            if (resultCode == RESULT_CANCELED) {
                premiaSelectView.removeAllViews();
                premiaCreate();

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
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
                    coinsEditor.putInt("coins", preferencesPrizes.getInt("coins", 0) + 200);
                    coinsEditor.putBoolean("moneyForTutorial", true);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void onShowPremia(Integer premiaID) {
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

    private void showInfo() {

        final Dialog builder = new Dialog(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setContentView(R.layout.inform);

        TextView text = builder.findViewById(R.id.textInform);
        final Button button = builder.findViewById(R.id.buttonInformOK);
        text.setText("Похоже это твой первый визит в игру МузТус! Рекомендуем сперва пройти небольшое обучение, чтобы разобраться что к чему. К тому же, если пройдешь обучение, получишь небольшой приятный стартовый бонус.");

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
