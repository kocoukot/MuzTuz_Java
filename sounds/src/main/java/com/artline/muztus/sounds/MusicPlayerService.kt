package com.artline.muztus.sounds

import android.content.Context
import android.media.MediaPlayer

class MusicPlayerService(context: Context) {

    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer.create(context, BG_MUSIC_RES)
    }
    private val BG_MUSIC_RES = R.raw.background_music
    private var soundPosition = 0

    fun start() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun pause() {
        soundPosition = mediaPlayer.currentPosition
        mediaPlayer.pause()
    }

    fun resume() {
        mediaPlayer.seekTo(soundPosition)
        mediaPlayer.start()
    }
}