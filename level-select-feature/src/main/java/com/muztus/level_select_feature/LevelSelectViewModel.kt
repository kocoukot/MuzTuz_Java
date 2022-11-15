package com.muztus.level_select_feature

import com.muztus.core_mvi.BaseViewModel
import com.muztus.domain_layer.usecase.GetPremiumDataUseCase
import com.muztus.level_select_feature.model.LevelActions
import com.muztus.level_select_feature.model.LevelSelectActions
import com.muztus.level_select_feature.model.LevelSelectRoute
import com.muztus.level_select_feature.model.LevelSelectState
import kotlinx.coroutines.flow.MutableStateFlow

class LevelSelectViewModel(
    private val selectedPremium: Int,
    private val getPremiumDataUseCase: GetPremiumDataUseCase
) : BaseViewModel.Base<LevelSelectState, LevelSelectActions>(
    mState = MutableStateFlow(LevelSelectState())
), LevelActions {

    init {
        updateInfo { copy(data = getPremiumDataUseCase(selectedPremium)) }
    }

    override fun setInputActions(action: LevelSelectActions) {
        action.handle(this)
    }

    override fun selectLevel(selectedLevel: Int) {
        sendRoute(LevelSelectRoute.GoGameLevel(selectedPremium, selectedLevel))
    }
}