package com.muztus.domain_layer.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.muztus.core.theme.MTTheme

interface GameLevelModel {

    fun checkUserInput(userInput: String): Boolean = false

    fun hintsRow(): List<HintModel> = emptyList()

    @Composable
    fun GetLevelImage(modifier: Modifier) = Unit

    @Composable
    fun GetLevelSongHint(modifier: Modifier) = Unit

    @Composable
    fun GetLettersAmount(modifier: Modifier) = Unit

    fun getCorrectAnswer(): String = ""


    fun lettersAmountHintUse(hintUse: HintUse) = Unit
    fun onOneLetterHintUse(hintUse: HintUse, letterIndex: Int) = Unit
    fun songHintUse(hintUse: HintUse) = Unit

    fun isSolved(): Boolean = false

    fun getLevelHintsState(): LevelHints

    fun countLevelDuration(currentDuration: Long): Long = 0

    data class Base(
        private val index: Int,
        private val premiumIndex: Int,
        private val correctAnswers: List<String>,
        private val levelHints: LevelHints,
        private val levelImage: Int,
        private val songName: String,
        private val isSolved: Boolean,
        private val gameDuration: Long
    ) : GameLevelModel {

        override fun checkUserInput(userInput: String): Boolean =
            correctAnswers.any { it.equals(userInput.trim().lowercase(), true) }

        override fun hintsRow(): List<HintModel> = levelHints.getHintsList()

        @Composable
        override fun GetLevelImage(modifier: Modifier) {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = levelImage),
                    contentDescription = "level image"
                )
            }
        }

        @Composable
        override fun GetLevelSongHint(modifier: Modifier) {
            levelHints.songHint.HintAnswer(modifier, hintText = songName)
        }

        @Composable
        override fun GetLettersAmount(modifier: Modifier) {
            if (isSolved) {
                Text(
                    color = MTTheme.colors.buttonPressed,
                    fontSize = 28.sp,
                    text = correctAnswers.first().chunked(1).joinToString(separator = " ")
                        .uppercase(),
                    modifier = modifier
                )
            } else if (levelHints.oneLetterHint.selectedLetterIndex >= 0) levelHints.oneLetterHint.HintAnswer(
                modifier = modifier,
                hintText = correctAnswers.first()
            )
            else {
                levelHints.letterAmountHint.HintAnswer(
                    modifier = modifier,
                    hintText = correctAnswers.first()
                )
            }
        }


        override fun getCorrectAnswer(): String = correctAnswers.first()


        override fun lettersAmountHintUse(hintUse: HintUse) {
            levelHints.letterAmountHint.onHintUsed(hintUse)
        }

        override fun onOneLetterHintUse(hintUse: HintUse, letterIndex: Int) {
            levelHints.letterAmountHint.onHintUsed()
            levelHints.oneLetterHint.onHintUsed(hintUse)
            levelHints.oneLetterHint =
                levelHints.oneLetterHint.copy(selectedLetterIndex = letterIndex)
        }

        override fun songHintUse(hintUse: HintUse) {
            levelHints.songHint.onHintUsed(hintUse)
        }

        override fun isSolved(): Boolean = isSolved
        override fun getLevelHintsState(): LevelHints = levelHints
        override fun countLevelDuration(currentDuration: Long): Long =
            gameDuration + currentDuration

    }


    object Empty : GameLevelModel {
        override fun getLevelHintsState(): LevelHints = LevelHints()
    }
}