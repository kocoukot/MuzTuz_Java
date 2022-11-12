package com.artline.muztus.domain.repos

import com.artline.muztus.domain.model.GameMainInfo

interface GameRepository {

    fun setGameMusicState(isOn: Boolean)

    fun setGameSoundsState(isOn: Boolean)

    fun setGameStarsAmount(amount: Int)

    fun setGameCoinsAmount(amount: Int)

    fun getGameInfo(): GameMainInfo

    fun setHelloMessageSeen()

}