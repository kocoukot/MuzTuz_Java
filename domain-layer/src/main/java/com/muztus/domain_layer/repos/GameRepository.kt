package com.muztus.domain_layer.repos

import com.muztus.domain_layer.model.GameMainInfo


interface GameRepository {

    fun setGameMusicState(isOn: Boolean)

    fun setGameSoundsState(isOn: Boolean)

    fun setGameStarsAmount(amount: Int)

    fun setGameCoinsAmount(amount: Int)

    fun getGameInfo(): GameMainInfo

    fun setHelloMessageSeen()

}