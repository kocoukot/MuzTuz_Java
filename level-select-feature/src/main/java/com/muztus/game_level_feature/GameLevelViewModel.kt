package com.muztus.game_level_feature

import com.muztus.core_mvi.BaseViewModel
import com.muztus.game_level_feature.data.GameLevelModel
import com.muztus.game_level_feature.data.LevelHints
import com.muztus.game_level_feature.model.GameLevelAction
import com.muztus.game_level_feature.model.GameLevelState
import com.muztus.game_level_feature.model.LevelAction
import com.muztus.level_select_feature.data.albumsList
import com.muztus.level_select_feature.data.correctAnswersList
import com.muztus.level_select_feature.data.premiaImagesList
import kotlinx.coroutines.flow.MutableStateFlow

class GameLevelViewModel(
    selectedPremium: Int,
    selectedLevel: Int
) : BaseViewModel.Base<GameLevelState, GameLevelAction>(
    mState = MutableStateFlow(GameLevelState())
), LevelAction {

    init {
        val currentLevel = GameLevelModel.Base(
            index = selectedLevel,
            correctAnswers = correctAnswersList[selectedPremium][selectedLevel],
            levelHints = LevelHints(),
            levelImage = premiaImagesList[selectedPremium][selectedLevel],
            songName = albumsList[selectedPremium][selectedLevel]
        )
        updateInfo {
            copy(data = currentLevel)
        }

    }

    override fun setInputActions(action: GameLevelAction) {
        action.handle(this)
    }

    override fun useLettersAmountHint() {

    }

    override fun useOneLetterHint() {

    }

    override fun useSongHint() {

    }

    override fun useAnswerHint() {

    }

    override fun onFreeCoinsClick() {

    }

    override fun onCheckInput(userInput: String) {
        println(getState().data.checkUserInput(userInput))
        if (getState().data.checkUserInput(userInput)) {

        } else {
            x
        }
    }
}