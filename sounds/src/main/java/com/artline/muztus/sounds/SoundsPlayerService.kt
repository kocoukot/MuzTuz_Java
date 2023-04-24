package com.artline.muztus.sounds

import android.content.Context
import android.media.MediaPlayer

class SoundsPlayerService {

    private lateinit var mp: MediaPlayer

    fun start(context: Context, sound: GameSound) {
        mp = MediaPlayer.create(context, sound.soundRes())
            .apply {
                setOnCompletionListener {
                    it.release()
                }
            }
        mp.start()
    }
}