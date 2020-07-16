package com.artline.muztus.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.artline.muztus.Premia;
import com.artline.muztus.R;

public class MusicPlayerService {
    private static final String TAG = "MusicManager";
    public static final int MUSIC_MENU = 0;

    private static MediaPlayer mp;
    private static int soundPosition = 0;


    public static void start(Context context, int music) {
        startM(context, music);
    }

    private static void startM(Context context, int music) {
        mp = MediaPlayer.create(context, R.raw.background_music);
        if (!mp.isPlaying()) {
            mp.start();
        }
    }

    private static boolean isPlaying() {
        return mp != null && mp.isPlaying();
    }

    public static void setPosition(Context context){
        soundPosition = mp.getCurrentPosition();
        Toast.makeText(context, soundPosition,Toast.LENGTH_LONG).show();
    }



    public static void pause() {
        if (mp != null) {
            soundPosition = mp.getCurrentPosition();
            mp.pause();
        }
    }

    public static void resume(Context context) {
        if (mp != null) {
            System.out.println("not null" + soundPosition);

            mp.seekTo(soundPosition);
            mp.start();
        } else {
            System.out.println(" null**");

            mp = MediaPlayer.create(context, R.raw.background_music);
            mp.start();
        }
    }


    public static void release() {

        Log.d(TAG, "Releasing media players");

            try {
                if (mp != null) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    mp.release();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
    }
}