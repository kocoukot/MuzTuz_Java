package com.muztus.game_level_feature.model

import com.muztus.game_level_feature.data.GameLevelModel

data class GameLevelState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: GameLevelModel = GameLevelModel.Empty(),
    val coinToast: Int = 0
)
