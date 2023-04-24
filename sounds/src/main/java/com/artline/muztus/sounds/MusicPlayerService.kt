package com.artline.muztus.sounds

import android.content.Context
import android.media.MediaPlayer

class MusicPlayerService(context: Context) {

    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer.create(context, BG_MUSIC_RES)
    }
    private val BG_MUSIC_RES = R.raw.background_music
    private var soundPosition = 0

    fun start(context: Context) {
//        mediaPlayer = MediaPlayer.create(context, BG_MUSIC_RES)
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

//    fun setPosition(context: Context?) {
//        soundPosition = mediaPlayer.currentPosition
//        Toast.makeText(context, soundPosition, Toast.LENGTH_LONG).show()
//    }

    fun pause() {
//        if (this::mediaPlayer.isInitialized) {
        soundPosition = mediaPlayer.currentPosition
        mediaPlayer.pause()
//        }
    }

    fun resume(context: Context?) {
//        if (this::mediaPlayer.isInitialized) {
//            println("not null $soundPosition")
        mediaPlayer.seekTo(soundPosition)
        mediaPlayer.start()
//        } else {
//            mediaPlayer = MediaPlayer.create(context, BG_MUSIC_RES)
//            mediaPlayer.start()
//        }
    }

//    fun release() {
//        try {
//            if (this::mediaPlayer.isInitialized) {
//                if (mediaPlayer.isPlaying) {
//                    mediaPlayer.stop()
//                }
//                mediaPlayer.release()
//            }
//        } catch (e: Exception) {
//        }
//    }
}