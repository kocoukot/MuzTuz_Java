package com.muztus.domain_layer.repos

import com.muztus.database.LevelInfoEntity
import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.GameSoundsInfo
import com.muztus.domain_layer.model.GameStatsInfo
import com.muztus.domain_layer.model.PremiaLevelModel
import kotlinx.coroutines.flow.Flow


interface GameRepository {

    val isFirstLaunch: Boolean

    fun getGameMainInfo(): GameStatsInfo

    fun setGameCoinsAmount(amount: Int, starsAmount: Int)

    suspend fun getSelectedPremiumData(selectedPremiumIndex: Int): List<PremiaLevelModel>

    fun resetStatistic()

    fun getSoundsState(): GameSoundsInfo

    fun setSoundsState(soundState: GameSoundsInfo)

    suspend fun getLevelInfo(premiumIndex: Int, levelIndex: Int): GameLevelModel

    suspend fun getDisksInfo(): Flow<List<LevelInfoEntity>>

    suspend fun setLevelInfo(levelInfo: LevelInfoEntity)

}