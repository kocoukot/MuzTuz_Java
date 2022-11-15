package com.muztus.domain_layer.repos


interface GameRepository {

    fun testCoins(): Int

    fun setGameMusicState(isOn: Boolean)

    fun setGameSoundsState(isOn: Boolean)

    fun setGameStarsAmount(amount: Int)

    fun setGameCoinsAmount(amount: Int)

//    fun getGameInfo(): GameMainInfo

    fun setHelloMessageSeen()

}