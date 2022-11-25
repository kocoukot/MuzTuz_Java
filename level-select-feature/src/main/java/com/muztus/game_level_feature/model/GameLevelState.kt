package com.muztus.game_level_feature.model

import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.HintModel

data class GameLevelState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: GameLevelModel = GameLevelModel.Empty,
    val coinToast: GameToast = GameToast.Empty,
    val showHintAlert: Boolean = false,
    val selectedHint: HintModel? = null,
    val showLetterAlert: String = "",
    val showCompleteLevelAlert: Boolean = false,
    val levelStarts: Int = 3,
    val coinsAmountWin: String = ""
)
