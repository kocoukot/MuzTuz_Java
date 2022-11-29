package com.muztus.level_select_feature.model

import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.HintModel
import com.muztus.domain_layer.model.HintUse
import com.muztus.domain_layer.model.PremiaLevelModel

data class LevelSelectState(
    val error: String = "",
    val isLoading: Boolean = false,
    val premiaLevelList: List<PremiaLevelModel> = emptyList(),
    val selectedLevel: SelectedLevel = SelectedLevel.Empty,
    val coinToast: GameToast = GameToast.Empty,
    val showCompleteLevelAlert: Boolean = false,
    val levelStarts: Int = 3,
    val coinsAmountWin: String = "",
    val showLetterAlert: String = "",
)

interface SelectedLevel {

    fun setSelectedHint(hint: HintModel?, isHintAlertVisible: Boolean = false): SelectedLevel = this

    fun onHintUse(hint: HintUse)

    fun hideUseHintAlert(): SelectedLevel = this

    fun lettersAmountUse(hint: HintUse)

    fun showSongHint(hint: HintUse)

    fun checkUserInput(input: String): Boolean

    fun getCorrectAnswer(): String

    fun onOneLetterHintUse(hint: HintUse, letterIndex: Int)

    fun getLevelIndex(): Int = 0
    data class SelectedLevelData(
        val selectedLevelModel: GameLevelModel = GameLevelModel.Empty,
        val showHintAlert: Boolean = false,
        val selectedHint: HintModel? = null,
        val gameLevelIndex: Int
    ) : SelectedLevel {

        override fun setSelectedHint(hint: HintModel?, isHintAlertVisible: Boolean): SelectedLevel =
            this.copy(selectedHint = hint, showHintAlert = isHintAlertVisible)

        override fun onHintUse(hint: HintUse) {
            selectedHint?.useHintTest(hint)
        }

        override fun hideUseHintAlert(): SelectedLevel = this.copy(showHintAlert = false)
        override fun lettersAmountUse(hint: HintUse) {
            selectedLevelModel.lettersAmountHintUse(hint)
        }

        override fun showSongHint(hint: HintUse) {
            selectedLevelModel.songHintUse(hint)
        }

        override fun checkUserInput(input: String): Boolean =
            selectedLevelModel.checkUserInput(input)

        override fun getCorrectAnswer(): String = selectedLevelModel.getCorrectAnswer()
        override fun onOneLetterHintUse(hint: HintUse, letterIndex: Int) {
            selectedLevelModel.onOneLetterHintUse(hint, letterIndex)
        }

        override fun getLevelIndex(): Int = gameLevelIndex

    }


    object Empty : SelectedLevel {
        override fun onHintUse(hint: HintUse) = Unit
        override fun lettersAmountUse(hint: HintUse) = Unit
        override fun showSongHint(hint: HintUse) = Unit
        override fun checkUserInput(input: String): Boolean = false
        override fun getCorrectAnswer(): String = ""
        override fun onOneLetterHintUse(hint: HintUse, letterIndex: Int) = Unit
    }
}
