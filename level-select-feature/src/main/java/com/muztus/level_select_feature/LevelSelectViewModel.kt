package com.muztus.level_select_feature

import androidx.lifecycle.viewModelScope
import com.artline.muztus.sounds.GameSound
import com.muztus.core.levelsdata.REQUARED_AMOUNT
import com.muztus.core_mvi.BaseViewModel
import com.muztus.database.LevelInfoEntity
import com.muztus.domain_layer.model.HintModel
import com.muztus.domain_layer.model.HintUse
import com.muztus.domain_layer.usecase.GetGameStarsCoinsUseCase
import com.muztus.domain_layer.usecase.GetPremiumDataUseCase
import com.muztus.domain_layer.usecase.SetCoinsAmountUseCase
import com.muztus.domain_layer.usecase.level.GetLevelInfoUseCase
import com.muztus.domain_layer.usecase.level.SetLevelInfoUseCase
import com.muztus.level_select_feature.model.GameToast
import com.muztus.level_select_feature.model.LevelSelectActions
import com.muztus.level_select_feature.model.LevelSelectRoute
import com.muztus.level_select_feature.model.LevelSelectState
import com.muztus.level_select_feature.model.NextPremiaAlert
import com.muztus.level_select_feature.model.SelectedLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LevelSelectViewModel(
    private val selectedPremium: Int,
    private val getPremiumDataUseCase: GetPremiumDataUseCase,

    private val getLevelInfoUseCase: GetLevelInfoUseCase,
    private val setCoinsAmountUseCase: SetCoinsAmountUseCase,
    private val getGameStarsCoinsUseCase: GetGameStarsCoinsUseCase,

    private val setLevelInfoUseCase: SetLevelInfoUseCase
) : BaseViewModel.Base<LevelSelectState, LevelSelectActions.Base>(
    mState = MutableStateFlow(LevelSelectState())
), LevelSelectActions, HintUse {

    private var gameStartTime: Long = 0
    private var priseCoins = PRISE_COINS

    init {
        viewModelScope.launch {
            getPremiumDataUseCase(selectedPremium).let { premiaLevelsList ->
                updateInfo {
                    copy(
                        premiaLevelList = premiaLevelsList,
                        nextPremiaOpened = if (((premiaLevelsList.filter { it.isPassed() }.size.toDouble() / premiaLevelsList.size) * 100) > REQUARED_AMOUNT)
                            NextPremiaAlert.IsShowed
                        else
                            NextPremiaAlert.NotShown
                    )
                }
            }
        }
    }

    override fun setInputActions(action: LevelSelectActions.Base) {
        action.handle(this)
    }

    override fun onGoBack() {
        when (getState().selectedLevel) {
            is SelectedLevel.SelectedLevelData -> updateInfo { copy(selectedLevel = SelectedLevel.Empty) }
            is SelectedLevel.Empty -> sendRoute(LevelSelectRoute.GoBack)
        }
    }

    override fun selectLevel(selectedLevel: Int) {
        println("item level selected $selectedLevel")
        viewModelScope.launch {
            val levelInfo = getLevelInfoUseCase(
                premiumIndex = selectedPremium,
                levelIndex = selectedLevel
            )

            updateInfo {
                priseCoins = PRISE_COINS
                copy(
                    levelStarts = 3,
                    selectedLevel = SelectedLevel.SelectedLevelData(
                        selectedLevelModel = levelInfo,
                        gameLevelIndex = selectedLevel
                    )

                )
            }
        }
        gameStartTime = System.currentTimeMillis()
    }

    override fun onHintSelect(selectedHint: HintModel) {
        if (selectedHint.canUseHint(getGameStarsCoinsUseCase.invoke().coinsAmount)) {
            updateInfo {
                copy(
                    selectedLevel = selectedLevel.setSelectedHint(
                        selectedHint,
                        true
                    )
                )
            }

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
                getState().selectedLevel.onHintUse(this)
            }.also {
                updateInfo { copy(selectedLevel = selectedLevel.hideUseHintAlert()) }
            }
    }

    override fun onCheckInput(userInput: String) {
        if (getState().selectedLevel.checkUserInput(userInput)) {
            var coinsAmountWin = "$priseCoins"
            val gameEndTime = System.currentTimeMillis()
            if ((gameEndTime - gameStartTime) < MAX_DURATION) {
                coinsAmountWin += " x2"
                priseCoins *= 2
            }
            updateInfo { copy(showCompleteLevelAlert = true, coinsAmountWin = coinsAmountWin) }
            setCoinsAmountUseCase.invoke(priseCoins, getState().levelStarts)
            sendRoute(LevelSelectRoute.UpdateCoins)


            val levelIndex = getState().selectedLevel.getLevelIndex()
            val list = getState().premiaLevelList.toMutableList()

            list[levelIndex] = list[levelIndex].setPassed()

            sendRoute(LevelSelectRoute.PlaySound(GameSound.SoundWin))

            println(getState().premiaLevelList)

            viewModelScope.launch {
                updateInfo {
                    copy(
                        premiaLevelList = list.toList(),
                    )
                }
                saveLevelState()
            }
        } else {

            updateInfo {
                copy(coinToast = GameToast.ToastInfo(toastText = R.string.wrong_answer))
            }
            sendRoute(LevelSelectRoute.PlaySound(GameSound.SoundWrongAnswer))
        }
    }

    override fun closeEndGameAlert() {
        updateInfo { copy(showCompleteLevelAlert = false) }
        onGoBack()

        if (selectedPremium < 6) {
            updateInfo {
                copy(
                    nextPremiaOpened = if (nextPremiaOpened == NextPremiaAlert.NotShown &&
                        (getState().premiaLevelList.filter { it.isPassed() }.size.toDouble() / getState().premiaLevelList.size) * 100 > REQUARED_AMOUNT
                    ) {
                        sendRoute(LevelSelectRoute.PlaySound(GameSound.SoundOpenPremia))
                        NextPremiaAlert.IsShowing
                    } else NextPremiaAlert.NotShown
                )
            }
        }
    }

    override fun useOneLetterHint(letterIndex: Int) {
        getState().selectedLevel.onOneLetterHintUse(this, letterIndex)
        updateInfo { copy(showLetterAlert = "") }
        saveLevelState()
    }

    override fun clearToastCoins() {
        updateInfo { copy(coinToast = GameToast.Empty) }
    }

    override fun closeNextPremiaAlert() {
        updateInfo {
            copy(
                nextPremiaOpened = NextPremiaAlert.IsShowed
            )
        }
    }

    override fun getFreeCoins() {
        sendRoute(LevelSelectRoute.GetFreeCoins)
    }

    override fun lettersAmount() {
        getState().selectedLevel.lettersAmountUse(this)
        saveLevelState()
    }

    override fun showLetterSelect() {
        updateInfo { copy(showLetterAlert = getState().selectedLevel.getCorrectAnswer()) }
    }

    override fun songHint() {
        getState().selectedLevel.showSongHint(this)
        saveLevelState()
    }

    override fun answerHint() {
//todo
    }

    override fun changeCoinsAmount(hintPrice: Int) {
        priseCoins -= HINT_USE_DECREASE
        setCoinsAmountUseCase(-hintPrice)
        updateInfo {
            copy(
                levelStarts = levelStarts - 1,
                selectedLevel = selectedLevel.setSelectedHint(null)
            )
        }
        sendRoute(LevelSelectRoute.UpdateCoins)
    }

    private fun saveLevelState() {
        viewModelScope.launch {
            setLevelInfoUseCase.invoke(
                LevelInfoEntity(
                    premiaIndex = selectedPremium,
                    levelIndex = getState().selectedLevel.getLevelIndex(),
                    isSolved = getState().premiaLevelList[getState().selectedLevel.getLevelIndex()].isPassed(),
                    levelDuration = getState().selectedLevel.getSelectedLevel()
                        .countLevelDuration(System.currentTimeMillis() - gameStartTime),
                    isLettersAmountUsed = getState().selectedLevel.getSelectedLevel()
                        .getLevelHintsState().letterAmountHint.isUsed,
                    selectedLetterIndex = getState().selectedLevel.getSelectedLevel()
                        .getLevelHintsState().oneLetterHint.selectedLetterIndex,
                    isSongOpened = getState().selectedLevel.getSelectedLevel()
                        .getLevelHintsState().songHint.isUsed,
                    isAnswerUsed = getState().selectedLevel.getSelectedLevel()
                        .getLevelHintsState().correctAnswerHint.isUsed,
                )
            )
        }
    }


    private companion object {
        private const val HINT_USE_DECREASE = 10
        private const val PRISE_COINS = 50
        private const val MAX_DURATION = 15000


    }

}