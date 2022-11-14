package com.muztus.game_level_feature.model

import com.muztus.domain_layer.model.HintModel

sealed class GameLevelAction {
    abstract fun handle(action: LevelAction)

    data class CheckUSerInput(private val userInput: String) : GameLevelAction() {
        override fun handle(action: LevelAction) = action.onCheckInput(userInput)
    }

    data class OnUserTapHint(private val selectedHint: HintModel) : GameLevelAction() {
        override fun handle(action: LevelAction) = action.onHint(selectedHint)
    }

    data class OnHintAlertDecision(private val isTrue: Boolean) : GameLevelAction() {
        override fun handle(action: LevelAction) = action.onAlertDecision(isTrue)
    }

    data class UseOneLetterHint(private val letterIndex: Int) : GameLevelAction() {
        override fun handle(action: LevelAction) = action.useOneLetterHint(letterIndex)
    }
}

interface LevelAction {

    fun onHint(selectedHint: HintModel)
    fun onAlertDecision(isTrue: Boolean)
    fun onCheckInput(userInput: String)
    fun useOneLetterHint(letterIndex: Int)

}
