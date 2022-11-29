package com.muztus.level_select_feature.model

import com.muztus.domain_layer.model.HintModel

interface LevelSelectActions {
    fun onGoBack()
    fun selectLevel(selectedLevel: Int)


    fun onHintSelect(selectedHint: HintModel)
    fun onHintAlertDecision(isTrue: Boolean)
    fun onCheckInput(userInput: String)
    fun useOneLetterHint(letterIndex: Int)

    fun closeEndGameAlert()

    fun clearToastCoins()


    sealed class Base {
        abstract fun handle(action: LevelSelectActions)

        data class SelectLevel(private val selectedLevel: Int) : Base() {
            override fun handle(action: LevelSelectActions) = action.selectLevel(selectedLevel)
        }


        data class CheckUSerInput(private val userInput: String) : Base() {
            override fun handle(action: LevelSelectActions) = action.onCheckInput(userInput)
        }

        data class OnUserTapHint(private val selectedHint: HintModel) : Base() {
            override fun handle(action: LevelSelectActions) = action.onHintSelect(selectedHint)
        }

        data class OnHintAlertDecision(private val isTrue: Boolean) : Base() {
            override fun handle(action: LevelSelectActions) = action.onHintAlertDecision(isTrue)
        }

        data class UseOneLetterHint(private val letterIndex: Int) : Base() {
            override fun handle(action: LevelSelectActions) = action.useOneLetterHint(letterIndex)
        }

        object CloseEndGameAlert : Base() {
            override fun handle(action: LevelSelectActions) = action.closeEndGameAlert()
        }

        object ClearToastCoins : Base() {
            override fun handle(action: LevelSelectActions) = action.clearToastCoins()
        }


        object OnBackPressed : Base() {
            override fun handle(action: LevelSelectActions) = action.onGoBack()
        }
    }

}
