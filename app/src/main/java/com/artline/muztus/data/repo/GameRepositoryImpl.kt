package com.artline.muztus.data.repo

import com.artline.muztus.data.SharedPreferencesStorage
import com.muztus.domain_layer.model.GameMainInfo
import com.muztus.domain_layer.repos.GameRepository

class GameRepositoryImpl(
    private val sharedPref: SharedPreferencesStorage
) : GameRepository {
    override fun setGameMusicState(isOn: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setGameSoundsState(isOn: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setGameStarsAmount(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun setGameCoinsAmount(amount: Int) {
        TODO("Not yet implemented")
    }

    override fun getGameInfo(): GameMainInfo {
        TODO("Not yet implemented")
    }

    override fun setHelloMessageSeen() {
        TODO("Not yet implemented")
    }
}