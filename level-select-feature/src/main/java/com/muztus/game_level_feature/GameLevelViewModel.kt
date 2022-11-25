package com.muztus.game_level_feature

import com.muztus.core_mvi.BaseViewModel
import com.muztus.domain_layer.model.HintModel
import com.muztus.domain_layer.model.HintUse
import com.muztus.domain_layer.usecase.GetGameCoinsUseCase
import com.muztus.domain_layer.usecase.GetLevelInfoUseCase
import com.muztus.domain_layer.usecase.SetCoinsAmountUseCase
import com.muztus.game_level_feature.model.GameLevelAction
import com.muztus.game_level_feature.model.GameLevelRoute
import com.muztus.game_level_feature.model.GameLevelState
import com.muztus.game_level_feature.model.GameToast
import com.muztus.level_select_feature.R
import kotlinx.coroutines.flow.MutableStateFlow

class GameLevelViewModel(
    selectedPremium: Int,
    selectedLevel: Int,
    private val getLevelInfoUseCase: GetLevelInfoUseCase,
    private val setCoinsAmountUseCase: SetCoinsAmountUseCase,
    private val getGameCoinsUseCase: GetGameCoinsUseCase
) : BaseViewModel.Base<GameLevelState, GameLevelAction.Base>(
    mState = MutableStateFlow(GameLevelState())
), GameLevelAction, HintUse {

    private var gameStartTime = System.currentTimeMillis()
    private var priseCoins = PRISE_COINS

    init {
        updateInfo {
            copy(
                data = getLevelInfoUseCase(
                    premiumIndex = selectedPremium,
                    levelIndex = selectedLevel,
                )
            )
        }
    }

    override fun setInputActions(action: GameLevelAction.Base) {
        action.handle(this)
    }

    override fun onHintSelect(selectedHint: HintModel) {
        if (selectedHint.canUseHint(getGameCoinsUseCase.invoke().coinsAmount)) {
            updateInfo { copy(selectedHint = selectedHint, showHintAlert = true) }
        } else {
            updateInfo {
                copy(
                    coinToast = GameToast.ToastWithArgs(
                        toastText = R.string.not_enough_coins_toast,
                        argument = selectedHint.hintCost()
                    )
                )
            }
        }
    }

    override fun onHintAlertDecision(isTrue: Boolean) {
        isTrue
            .takeIf { it }
            ?.let {
                getState().selectedHint?.useHintTest(this)
            }.also {
                updateInfo { copy(showHintAlert = false) }
            }
    }

    override fun showLetterSelect() {
        updateInfo { copy(showLetterAlert = getState().data.getCorrectAnswer()) }
    }

    override fun lettersAmount() {
        getState().data.lettersAmountHintUse(this)
    }

    override fun useOneLetterHint(letterIndex: Int) {
        getState().data.onOneLetterHintUse(this, letterIndex)
        updateInfo { copy(showLetterAlert = "") }
    }

    override fun closeEndGameAlert() {
        updateInfo { copy(showCompleteLevelAlert = false) }
        sendRoute(GameLevelRoute.CloseGameLevel)
    }

    override fun clearToastCoins() {
        updateInfo { copy(coinToast = GameToast.Empty) }
    }


    override fun songHint() {
        getState().data.songHintUse(this)
    }

    override fun answerHint() {

    }

    override fun changeCoinsAmount(hintPrice: Int) {
        priseCoins -= HINT_USE_DECREASE
        setCoinsAmountUseCase(-hintPrice)
        updateInfo { copy(levelStarts = levelStarts - 1, selectedHint = null) }
        sendRoute(GameLevelRoute.UpdateCoins)
    }

    override fun onCheckInput(userInput: String) {
        println(getState().data.checkUserInput(userInput))
        if (getState().data.checkUserInput(userInput)) {
            var coinsAmountWin = "$priseCoins"
            val gameEndTime = System.currentTimeMillis()
            if ((gameEndTime - gameStartTime) < MAX_DURATION) {
                coinsAmountWin += " x2"
                priseCoins *= 2
            }
            updateInfo { copy(showCompleteLevelAlert = true, coinsAmountWin = coinsAmountWin) }
            setCoinsAmountUseCase.invoke(priseCoins, getState().levelStarts)
            sendRoute(GameLevelRoute.UpdateCoins)
        } else {
            updateInfo {
                copy(coinToast = GameToast.ToastInfo(toastText = R.string.wrong_answer))
            }
        }
    }

    private companion object {
        private const val HINT_USE_DECREASE = 10
        private const val PRISE_COINS = 50
        private const val MAX_DURATION = 15000


    }
}