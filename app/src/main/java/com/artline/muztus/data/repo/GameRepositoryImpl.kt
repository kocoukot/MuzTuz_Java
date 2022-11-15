package com.artline.muztus.data.repo

import com.artline.muztus.data.SharedPreferencesStorage
import com.muztus.domain_layer.repos.GameRepository

class GameRepositoryImpl(
    private val sharedPreferencesStorage: SharedPreferencesStorage
) : GameRepository {

    override fun testCoins(): Int = sharedPreferencesStorage.get<Int>(ARG_COINS) ?: 0

    override fun setGameMusicState(isOn: Boolean) {

    }

    override fun setGameSoundsState(isOn: Boolean) {

    }

    override fun setGameStarsAmount(amount: Int) {

    }

    override fun setGameCoinsAmount(amount: Int) {
        val coins = sharedPreferencesStorage.get<Int>(ARG_COINS) ?: 0
        sharedPreferencesStorage[ARG_COINS] = coins + amount
    }


    override fun setHelloMessageSeen() {

    }

    companion object {
        const val ARG_COINS = "game_coins"
    }
}