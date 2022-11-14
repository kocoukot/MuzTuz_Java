package com.muztus.game_level_feature

import com.muztus.core_mvi.BaseViewModel
import com.muztus.domain_layer.model.HintModel
import com.muztus.domain_layer.model.HintUse
import com.muztus.domain_layer.model.LevelHints
import com.muztus.game_level_feature.data.GameLevelModel
import com.muztus.game_level_feature.model.GameLevelAction
import com.muztus.game_level_feature.model.GameLevelState
import com.muztus.game_level_feature.model.LevelAction
import com.muztus.level_select_feature.data.albumsList
import com.muztus.level_select_feature.data.correctAnswersList
import com.muztus.level_select_feature.data.premiaImagesList
import kotlinx.coroutines.flow.MutableStateFlow

class GameLevelViewModel(
    private val selectedPremium: Int,
    private val selectedLevel: Int,
) : BaseViewModel.Base<GameLevelState, GameLevelAction>(
    mState = MutableStateFlow(GameLevelState())
), LevelAction, HintUse {

    init {
        updateInfo {
            copy(
                data = GameLevelModel.Base(
                    index = selectedLevel,
                    premiumIndex = selectedPremium,
                    correctAnswers = correctAnswersList[selectedPremium][selectedLevel],
                    levelHints = LevelHints(),
                    levelImage = premiaImagesList[selectedPremium][selectedLevel],
                    songName = albumsList[selectedPremium][selectedLevel],
                    isSolved = false
                )
            )
        }
    }

    override fun setInputActions(action: GameLevelAction) {
        action.handle(this)
    }

    override fun onHintSelect(selectedHint: HintModel) {
        if (selectedHint.canUseHint(450)) {
            updateInfo { copy(showHintAlert = selectedHint) }
        } else {
            updateInfo { copy(coinToast = selectedHint.hintCost()) }
        }
    }

    override fun onHintAlertDecision(isTrue: Boolean) {
        isTrue
            .takeIf { it }
            ?.let {
                getState().showHintAlert?.useHintTest(this)
            }.also { updateInfo { copy(showHintAlert = null) } }
    }

    override fun lettersAmount() {
        getState().data.lettersAmountHintUse()
    }

    override fun showOnLetterSelect() {
        updateInfo { copy(showLetterAlert = getState().data.getCorrectAnswer()) }
    }

    override fun useOneLetterHint(letterIndex: Int) {
        getState().data.onOneLetterHintUse(letterIndex)
        updateInfo { copy(showLetterAlert = "") }
    }

    override fun clearToastCoins() {
        updateInfo { copy(coinToast = 0) }
    }


    override fun songHint() {
        getState().data.songHintUse()
    }

    override fun answerHint() {
    }

    override fun onCheckInput(userInput: String) {
//        println(getState().data.checkUserInput(userInput))
//        if (getState().data.checkUserInput(userInput)) {
//
//        } else {
//
//        }
    }
}