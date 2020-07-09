package com.example.test.audio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.test.R;

public class SoundsPlayerService {
    private static final String TAG = "MusicManager";
   public static final int SOUND_WIN = 0;
    public static final int SOUND_WRONG_ANSWER = 1;
    public static final int SOUND_WARNING_VIEW = 2;
    public static final int SOUND_SPEND_MONEY = 3;
    public static final int SOUND_RESET_ALL = 4;
    public static final int SOUND_OPEN_PREMIA = 5;
    public static final int SOUND_ON_MUSIC = 6;
    public static final int SOUND_OFF_MUSIC = 7;
    public static final int SOUND_GOT_COINS = 8;
    public static final int SOUND_APPEAR_VIEW = 9;
    public static final int SOUND_GAMEOVER= 10;


    private static MediaPlayer mp;


    public static void start(Context context, int music, boolean isSoundOn) {
        if (isSoundOn){
        startM(context, music);
        }else {
            return;
        }
    }

    private static void startM(Context context, int music) {
        switch (music) {
            case 0:
                mp = MediaPlayer.create(context, R.raw.win);
                mp.start();
                break;
            case 1:
                mp = MediaPlayer.create(context, R.raw.wrong_answer);
                mp.start();
                break;
            case 2:
                mp = MediaPlayer.create(context, R.raw.warning_view);
                mp.start();
                break;
            case 3:
                mp = MediaPlayer.create(context, R.raw.spend_money);
                mp.start();
                break;
            case 4:
                mp = MediaPlayer.create(context, R.raw.reset_all);
                mp.start();
                break;
            case 5:
                mp = MediaPlayer.create(context, R.raw.open_premia);
                mp.start();
                break;
            case 6:
                mp = MediaPlayer.create(context, R.raw.on_music);
                mp.start();
                break;
            case 7:
                mp = MediaPlayer.create(context, R.raw.off_music);
                mp.start();
                break;
            case 8:
                mp = MediaPlayer.create(context, R.raw.bought_coins);
                mp.start();

                release();
                break;
            case 9:
                mp = MediaPlayer.create(context, R.raw.appearing_view);
                mp.start();
                release();
                break;
            case 10:
                mp = MediaPlayer.create(context, R.raw.answer_help);
                mp.start();
                release();
                break;
        }
    }

    private static boolean isPlaying() {
        return mp != null && mp.isPlaying();
    }



    public static void release() {
        try {

            if (mp != null) {
                if (!mp.isPlaying()) {
                    System.out.println("released");
                    mp.release();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}