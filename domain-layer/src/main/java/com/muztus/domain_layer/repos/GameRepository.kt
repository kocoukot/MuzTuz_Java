package com.muztus.domain_layer.repos

import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.GameSoundsInfo
import com.muztus.domain_layer.model.GameStatsInfo
import com.muztus.domain_layer.model.PremiaLevelModel


interface GameRepository {

    fun getGameMainInfo(): GameStatsInfo

    fun setGameCoinsAmount(amount: Int, starsAmount: Int)

    fun getLevelInfo(premiumIndex: Int, levelIndex: Int): GameLevelModel

    fun getSelectedPremiumData(selectedPremiumIndex: Int): List<PremiaLevelModel>

    fun resetStatistic()

    fun getSoundsState(): GameSoundsInfo

    fun setSoundsState(soundState: GameSoundsInfo)

    val isFirstLaunch: Boolean

}