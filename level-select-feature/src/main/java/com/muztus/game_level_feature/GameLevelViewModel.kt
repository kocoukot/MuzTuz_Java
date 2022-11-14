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
        val currentLevel = GameLevelModel.Base(
            index = selectedLevel,
            premiumIndex = selectedPremium,
            correctAnswers = correctAnswersList[selectedPremium][selectedLevel],
            levelHints = LevelHints(),
            levelImage = premiaImagesList[selectedPremium][selectedLevel],
            songName = albumsList[selectedPremium][selectedLevel],
            isSolved = false
        )
        updateInfo {
            copy(data = currentLevel)
        }
    }

    override fun setInputActions(action: GameLevelAction) {
        action.handle(this)
    }

    override fun onHint(selectedHint: HintModel) {
        if (selectedHint.canUseHint(3000)) {
            updateInfo { copy(showHintAlert = selectedHint) }
        } else {
            updateInfo { copy(coinToast = selectedHint.hintCost()) }
        }
    }

    override fun onAlertDecision(isTrue: Boolean) {
        if (isTrue) {
            getState().showHintAlert?.useHintTest(this)
        }
        updateInfo { copy(showHintAlert = null) }
    }

    override fun onCheckInput(userInput: String) {
//        println(getState().data.checkUserInput(userInput))
//        if (getState().data.checkUserInput(userInput)) {
//
//        } else {
//
//        }
    }

    override fun lettersAmount() {
        updateInfo { copy(data = getState().data.lettersAmountHintUse()) }

    }

    override fun useOneLetterHint(letterIndex: Int) {
        updateInfo {
            copy(
                showLetterAlert = "",
                data = getState().data.onOneLetterHintUse(letterIndex)
            )
        }
    }

    override fun showOnLetterSelect() {
        updateInfo { copy(showLetterAlert = getState().data.getCorrectAnswer()) }
    }

    override fun songHint() {
        updateInfo { copy(showLetterAlert = "", data = getState().data.songHintUse()) }
    }

    override fun answerHint() {
    }
}