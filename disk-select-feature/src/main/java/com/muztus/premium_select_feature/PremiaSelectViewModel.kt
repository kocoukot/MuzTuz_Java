package com.muztus.premium_select_feature

import androidx.lifecycle.viewModelScope
import com.muztus.core.levelsdata.REQUARED_AMOUNT
import com.muztus.core.levelsdata.premiaDisksList
import com.muztus.core.levelsdata.premiaImagesList
import com.muztus.core_mvi.BaseViewModel
import com.muztus.domain_layer.model.PremiumModel
import com.muztus.domain_layer.usecase.GetPremiumStateUseCase
import com.muztus.premium_select_feature.model.PremiaSelectActions
import com.muztus.premium_select_feature.model.PremiaSelectState
import com.muztus.premium_select_feature.model.PremiumAction
import com.muztus.premium_select_feature.model.PremiumSelectRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PremiaSelectViewModel(
    private val getPremiumStateUseCase: GetPremiumStateUseCase
) : BaseViewModel.Base<PremiaSelectState, PremiaSelectActions>(
    mState = MutableStateFlow(PremiaSelectState())
), PremiumAction {

    init {
        viewModelScope.launch {
            getPremiumStateUseCase.invoke().collect { premiaList ->

                val premiumData = premiaDisksList.mapIndexed { index, item ->
                    val passedPremiumLevelsAmount =
                        premiaList.filter { it.premiaIndex == index && it.isSolved }.size
                    val isPremiaOpened = when (index) {
                        0, 1 -> true
                        premiaDisksList.lastIndex -> false
                        else -> {
                            val solvedAmount =
                                (premiaList.filter { it.premiaIndex == (index - 1) && it.isSolved }.size).toDouble()
                            val size = (premiaImagesList[index - 1].size).toDouble()
                            ((solvedAmount / size) * 100) > REQUARED_AMOUNT
                        }
                    }

                    val imageIndex =
                        if (index == 0) passedPremiumLevelsAmount
                        else if (index < 2) (((passedPremiumLevelsAmount.toDouble()) / item.size) * 10).toInt()
                        else {
                            if (isPremiaOpened) passedPremiumLevelsAmount + 1 else 0
                        }

                    PremiumModel.Base(
                        premiumNumber = index,
                        premiumProgress = passedPremiumLevelsAmount,
                        premiumLvlAmount = item.size,
                        premiumImage = item[imageIndex],
                        isOpened = isPremiaOpened
                    )
                }


                updateInfo {
                    copy(data = premiumData)
                }
            }
//                .launchIn(viewModelScope)

        }
    }

    override fun setInputActions(action: PremiaSelectActions) {
        action.handel(this)
    }

    override fun selectPremia(premia: PremiumModel) {
        sendRoute(
            if (premia.premiumNumber() == 0)
                PremiumSelectRoute.GoTutorial
            else
                PremiumSelectRoute.GoLevelSelect(premia.premiumNumber())
        )
    }

}