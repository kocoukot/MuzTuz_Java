package com.muztus.domain_layer.model

import com.muztus.database.LevelInfoEntity

data class StatisticModel(
    val summaryTime: Long,
    val levelsPassed: Int,
    val hintsUsed: Int,
    val fastestLevel: LevelInfoEntity,
    val longestLevel: LevelInfoEntity,
)
