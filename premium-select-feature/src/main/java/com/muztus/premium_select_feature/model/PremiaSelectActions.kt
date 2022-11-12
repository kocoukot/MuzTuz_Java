package com.muztus.premium_select_feature.model

sealed class PremiaSelectActions {
    abstract fun handel(action: PremiumAction)

    data class SelectedPremia(private val premia: PremiumModel) : PremiaSelectActions() {
        override fun handel(action: PremiumAction) = action.selectPremia(premia)

    }
}

interface PremiumAction {
    fun selectPremia(premia: PremiumModel)
}