package com.muztus.game_level_feature.model

import com.muztus.domain_layer.model.HintModel

interface GameLevelAction {
    fun onHintSelect(selectedHint: HintModel)
    fun onHintAlertDecision(isTrue: Boolean)
    fun onCheckInput(userInput: String)
    fun useOneLetterHint(letterIndex: Int)

    fun closeEndGameAlert()

    fun clearToastCoins()

    sealed class Base {
        abstract fun handle(action: GameLevelAction)

        data class CheckUSerInput(private val userInput: String) : Base() {
            override fun handle(action: GameLevelAction) = action.onCheckInput(userInput)
        }

        data class OnUserTapHint(private val selectedHint: HintModel) : Base() {
            override fun handle(action: GameLevelAction) = action.onHintSelect(selectedHint)
        }

        data class OnHintAlertDecision(private val isTrue: Boolean) : Base() {
            override fun handle(action: GameLevelAction) = action.onHintAlertDecision(isTrue)
        }

        data class UseOneLetterHint(private val letterIndex: Int) : Base() {
            override fun handle(action: GameLevelAction) = action.useOneLetterHint(letterIndex)
        }

        object CloseEndGameAlert : Base() {
            override fun handle(action: GameLevelAction) = action.closeEndGameAlert()
        }

        object ClearToastCoins : Base() {
            override fun handle(action: GameLevelAction) = action.clearToastCoins()
        }
    }
}

