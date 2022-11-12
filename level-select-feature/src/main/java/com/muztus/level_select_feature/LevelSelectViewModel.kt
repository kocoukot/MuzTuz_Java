package com.muztus.level_select_feature

import com.muztus.core_mvi.BaseViewModel
import com.muztus.data.PremiaLevelModel
import com.muztus.level_select_feature.data.premiaImagesList
import com.muztus.level_select_feature.model.LevelActions
import com.muztus.level_select_feature.model.LevelSelectActions
import com.muztus.level_select_feature.model.LevelSelectRoute
import com.muztus.level_select_feature.model.LevelSelectState
import kotlinx.coroutines.flow.MutableStateFlow

class LevelSelectViewModel(
    private val selectedPremium: Int
) : BaseViewModel.Base<LevelSelectState, LevelSelectActions>(
    mState = MutableStateFlow(LevelSelectState())
), LevelActions {

    init {
        val levelData = premiaImagesList[selectedPremium].mapIndexed { index, img ->
            PremiaLevelModel.Base(
                levelIndex = index,
                isLevelPassed = true,
                levelImage = img,
            )

        }
        updateInfo { copy(data = levelData) }
    }

    override fun setInputActions(action: LevelSelectActions) {
        action.handle(this)
    }

    override fun selectLevel(selectedLevel: Int) {
        sendRoute(LevelSelectRoute.GoGameLevel(selectedPremium, selectedLevel))
    }
}