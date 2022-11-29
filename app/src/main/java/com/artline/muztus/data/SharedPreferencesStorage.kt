package com.artline.muztus.data

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesStorage(private val sharedPreferences: SharedPreferences) {

    operator fun set(key: String, value: Any?) = sharedPreferences.edit {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
        }
    }

    operator fun <T> get(key: String): T? = sharedPreferences.all[key] as? T


    fun getCoinsAmount() = get<Int>(ARG_COINS) ?: 0

    fun getStarsAmount() = get<Int>(ARG_STARS) ?: 0

    fun addCoins(amount: Int) {
        set(ARG_COINS, getCoinsAmount() + amount)
    }

    fun addStars(amount: Int) {
        set(ARG_STARS, getStarsAmount() + if (amount < 0) 0 else amount)
    }

    fun checkFirstLaunch(): Boolean {
        val isFirst = (get<Boolean>(ARG_IS_FIRST_LAUNCH) ?: true)
        if (isFirst) set(ARG_IS_FIRST_LAUNCH, false)
        return isFirst
    }

    fun getMusicState() = (get<Boolean>(ARG_MUSIC) ?: true)
    fun setMusicState(isOn: Boolean) {
        set(ARG_MUSIC, isOn)
    }

    fun getSoundState() = (get<Boolean>(ARG_SOUNDS) ?: true)
    fun setSoundState(isOn: Boolean) {
        set(ARG_SOUNDS, isOn)
    }


    companion object Keys {
        private const val ARG_MUSIC = "music_state"
        private const val ARG_SOUNDS = "sounds_state"


        private const val ARG_COINS = "game_coins"
        private const val ARG_STARS = "game_stars"
        private const val ARG_IS_FIRST_LAUNCH = "is_first_launch"

        const val fcmTokenKey = "FcmTokenKey"
    }
}
