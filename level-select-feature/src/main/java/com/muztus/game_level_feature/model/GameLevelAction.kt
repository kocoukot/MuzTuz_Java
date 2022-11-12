package com.muztus.game_level_feature.model

import com.muztus.game_level_feature.data.HintModel

sealed class GameLevelAction {
    abstract fun handle(action: LevelAction)

    object UseLettersAmountHint : GameLevelAction() {
        override fun handle(action: LevelAction) = action.useLettersAmountHint()
    }

    data class OnUserTapHint(private val selectedHint: HintModel) : GameLevelAction() {
        override fun handle(action: LevelAction) = action.onHint(selectedHint)
    }

    data class OnHintAlertDecision(private val isTrue: Boolean) : GameLevelAction() {
        override fun handle(action: LevelAction) = action.onAlertDecision(isTrue)
    }


    object UseOneLetterHint : GameLevelAction() {
        override fun handle(action: LevelAction) = action.useOneLetterHint()
    }

    object UseSongHint : GameLevelAction() {
        override fun handle(action: LevelAction) = action.useSongHint()
    }

    object UseAnswerHint : GameLevelAction() {
        override fun handle(action: LevelAction) = action.useAnswerHint()
    }

    object OnFreeCoinsClick : GameLevelAction() {
        override fun handle(action: LevelAction) = action.onFreeCoinsClick()
    }

    data class CheckUSerInput(private val userInput: String) : GameLevelAction() {
        override fun handle(action: LevelAction) = action.onCheckInput(userInput)
    }
}

interface LevelAction {

    fun onHint(selectedHint: HintModel)
    fun onAlertDecision(isTrue: Boolean)
    fun onCheckInput(userInput: String)


    fun useLettersAmountHint()
    fun useOneLetterHint()
    fun useSongHint()
    fun useAnswerHint()
    fun onFreeCoinsClick()


}
