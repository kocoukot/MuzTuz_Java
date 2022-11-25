package com.muztus.domain_layer.repos

import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.GameMainInfo
import com.muztus.domain_layer.model.PremiaLevelModel


interface GameRepository {

    fun getGameMainInfo(): GameMainInfo

    fun setGameMusicState(isOn: Boolean)

    fun setGameSoundsState(isOn: Boolean)

    fun setGameCoinsAmount(amount: Int, starsAmount: Int)

    fun getLevelInfo(premiumIndex: Int, levelIndex: Int): GameLevelModel

    fun getSelectedPremiumData(selectedPremiumIndex: Int): List<PremiaLevelModel>

    fun resetStatistic()

    val isFirstLaunch: Boolean

}