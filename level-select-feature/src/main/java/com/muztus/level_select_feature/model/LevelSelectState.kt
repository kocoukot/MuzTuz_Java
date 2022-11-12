package com.muztus.level_select_feature.model

import com.muztus.data.PremiaLevelModel

data class LevelSelectState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: List<PremiaLevelModel> = emptyList()
)