package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.audio.MusicPlayerService;
import com.example.test.audio.SoundsPlayerService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class Shop extends AppCompatActivity implements RewardedVideoAdListener {

    private SharedPreferences preferencesPrizes;

    private final String PREFERENCESPrizes = "Preferences.prizes";
    private final String PREFERENCESSounds = "Preferences.sounds";
    private SharedPreferences preferencesSounds;
    private boolean musicOff = false;
    private ImageView lightsView;
    private Toast toast1;


    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        preferencesPrizes = getSharedPreferences(PREFERENCESPrizes, MODE_PRIVATE);
        preferencesSounds =  getSharedPreferences(PREFERENCESSounds, MODE_PRIVATE);
        lightsView = findViewById(R.id.lightsView);

        lightsView.setBackgroundResource(R.drawable.anim_lights);
        AnimationDrawable animationLeft =  (AnimationDrawable) lightsView.getBackground();
        animationLeft.start();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }

    public void onGetFreeCoinsShop(View view) {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {
            showToast("Что-то пошло не так. Попробуй еще раз!");

        }
    }

    @Override
    public void onRewarded(RewardItem reward) {
        SharedPreferences.Editor prizesEditor = preferencesPrizes.edit();
        prizesEditor.putInt("coins", preferencesPrizes.getInt("coins", 0) + 150);
        prizesEditor.apply();
        SoundsPlayerService.start(this, SoundsPlayerService.SOUND_GOT_COINS,preferencesSounds.getBoolean("soundsPlay", true));

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
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

    private void showToast(String message){
        toast1 = Toast.makeText(this,message , Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER, 0, 50);
        toast1.show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
        showToast("Готово!");
    }

    @Override
    public void onRewardedVideoCompleted() {
    }
}
