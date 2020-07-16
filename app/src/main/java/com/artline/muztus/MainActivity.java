package com.artline.muztus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artline.muztus.audio.MusicPlayerService;
import com.artline.muztus.audio.SoundsPlayerService;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private Intent intent;

    private TextView textViewCoins;
    private TextView textViewStars;


    private ImageView musicButton, soundsButton;


    private SharedPreferences preferencesProgress, preferencesPrizes;

    private final String PREFERENCESProgress = "Preferences.progress";
    private final String PREFERENCESPrizes = "Preferences.prizes";
    private final String PREFERENCESSounds = "Preferences.sounds";
    private SharedPreferences preferencesSounds;
    private ImageView lightsView;
    private boolean musicOff = true;

    private RewardedVideoAd mRewardedVideoAd;
    private Toast toast1;

    String freeID = "ca-app-pub-8364051315582457/5955782184";

    // test
  // String freeID =  "ca-app-pub-3940256099942544/5224354917";

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

        lightsView = findViewById(R.id.lightsView);

        Intent intentSC = new Intent(this, StartScreen.class);
        startActivity(intentSC);

        lightsView.setBackgroundResource(R.drawable.anim_lights);
        AnimationDrawable animationLeft = (AnimationDrawable) lightsView.getBackground();
        animationLeft.start();

        soundsStatusSet();
        coinsStarsUpDate();
        if (!preferencesPrizes.getBoolean("firstMesInMenu", false)) {
            SharedPreferences.Editor editor = preferencesPrizes.edit();
            editor.putBoolean("firstMesInMenu", true);
            editor.apply();
            showInfo();
        }


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
      //  MobileAds.initialize(this, "ca-app-pub-8364051315582457~3265789330");
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

    }

    public void onStartPlay(View view) {
        musicOff = true;
        intent = new Intent(this, PremiaChoose.class);
        intent.putExtra("musicOff", true);
        startActivityForResult(intent,1);

    }

    public void onStatistic(View view) {
        intent = new Intent(this, Statistika.class);
        musicOff = true;
        intent.putExtra("musicOff", true);

        startActivityForResult(intent,1);
    }

    public void onReset(View view) {
        showDialogMasseg();
    }

    public void onSozdateli(View view) {
        musicOff = true;

        intent = new Intent(this, Sozdateli.class);
        intent.putExtra("musicOff", true);

        startActivityForResult(intent,1);

    }

    private void soundsStatusSet(){
        if (preferencesSounds.getBoolean("musicPlay", true)){
            musicButton.setImageResource(R.drawable.buton_music_on);
         //   MusicPlayerService.start(this,MusicPlayerService.MUSIC_MENU);
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

        musicOff = true;
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
           MusicPlayerService.pause();
            editor.putBoolean("soundsPlay", false);

        } else {
            SoundsPlayerService.start(this, SoundsPlayerService.SOUND_ON_MUSIC,true);

            soundsButton.setImageResource(R.drawable.buton_sound_on);
          MusicPlayerService.resume(this);
            editor.putBoolean("soundsPlay", true);
        }
        editor.apply();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicPlayerService.release();
        mRewardedVideoAd.destroy(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mRewardedVideoAd.pause(this);

        if( !musicOff ){
            MusicPlayerService.pause();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        musicOff = true;
    }


    @Override
    protected void onResume() {
        mRewardedVideoAd.resume(this);

        super.onResume();
        if (!musicOff) {
            if (preferencesSounds.getBoolean("musicPlay", true)) {
                MusicPlayerService.resume(this);
            }
        }
        musicOff = false;
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
        SoundsPlayerService.start(this, SoundsPlayerService.SOUND_GOT_COINS, preferencesSounds.getBoolean("soundsPlay", true));
        coinsStarsUpDate();

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(freeID, new AdRequest.Builder().build());
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
    }


    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    private void showToast(String message) {
        toast1 = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER, 0, 50);
        toast1.show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                musicOff = false;
                return false;
            case KeyEvent.KEYCODE_BACK:
                musicOff = false;
                return false;
            case KeyEvent.KEYCODE_HOME:
                musicOff = false;
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
