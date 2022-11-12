package com.muztus.game_level_feature.model

sealed class GameLevelAction {
    abstract fun handle(action: LevelAction)

    object UseLettersAmountHint : GameLevelAction() {
        override fun handle(action: LevelAction) = action.useLettersAmountHint()
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
    fun useLettersAmountHint()
    fun useOneLetterHint()
    fun useSongHint()
    fun useAnswerHint()
    fun onFreeCoinsClick()

    fun onCheckInput(userInput: String)

}
