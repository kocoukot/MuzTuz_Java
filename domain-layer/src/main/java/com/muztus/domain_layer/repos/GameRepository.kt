package com.muztus.domain_layer.repos

import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.PremiaLevelModel


interface GameRepository {

    fun testCoins(): Int

    fun setGameMusicState(isOn: Boolean)

    fun setGameSoundsState(isOn: Boolean)

    fun setGameStarsAmount(amount: Int)

    fun setGameCoinsAmount(amount: Int)

//    fun getGameInfo(): GameMainInfo

    fun setHelloMessageSeen()

    fun getLevelInfo(premiumIndex: Int, levelIndex: Int): GameLevelModel

    fun getSelectedPremiumData(selectedPremiumIndex: Int): List<PremiaLevelModel>

    fun resetStatistic()

}