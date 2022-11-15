package com.muztus.game_level_feature.model

import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.HintModel

data class GameLevelState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: GameLevelModel = GameLevelModel.Empty,
    val coinToast: Int = 0,
    val showHintAlert: Boolean = false,
    val selectedHint: HintModel? = null,
    val showLetterAlert: String = ""
)
