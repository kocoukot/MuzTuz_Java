package com.muztus.game_level_feature.data

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.muztus.level_select_feature.R

interface HintModel {


    fun canUseHint(totalAmount: Int): Boolean

    fun useHint()

    @Composable
    fun HintImage(modifier: Modifier)

    abstract class Abstract(
        private val hintCost: Int,
        private val hintImage: Int,
    ) : HintModel {

        @Composable
        override fun HintImage(modifier: Modifier) {
            Image(
                modifier = modifier,
                painter = painterResource(id = hintImage), contentDescription = null
            )
        }

        override fun canUseHint(totalAmount: Int): Boolean = totalAmount >= hintCost
    }

    data class LetterAmountHint(
        private val hintName: String = "Letters amount hint",
        private val isUsed: Boolean = false,
        private val hintImage: Int = R.drawable.podskazka_kolichestvo_bukv,
    ) : Abstract(100, hintImage) {
        override fun useHint() {

        }

    }


    data class OneLetterHint(
        private val hintName: String = "One letter hint",
        private val isUsed: Boolean = false,
        private val hintImage: Int = R.drawable.podskazka_lubay_bukva,
        private val selectedLetters: Set<Int> = setOf()
    ) : Abstract(200, hintImage) {

        override fun useHint() {

        }
    }

    data class SongHint(
        private val hintName: String = "One letter hint",
        private val isUsed: Boolean = false,
        private val hintImage: Int = R.drawable.podskazka_albom_pesny,
    ) : Abstract(300, hintImage) {

        override fun useHint() {

        }
    }

    data class CorrectAnswer(
        private val hintName: String = "One letter hint",
        private val isUsed: Boolean = false,
        private val hintImage: Int = R.drawable.podskazka_podskazok,
    ) : Abstract(500, hintImage) {
        override fun useHint() {

        }
    }
}


data class LevelHints(
    val letterAmountHint: HintModel = HintModel.LetterAmountHint(),
    val oneLetterHint: HintModel = HintModel.OneLetterHint(),
    val songHint: HintModel = HintModel.SongHint(),
    val correctAnswerHint: HintModel = HintModel.CorrectAnswer(),
) {
    fun getHintsList() = listOf(letterAmountHint, oneLetterHint, songHint, correctAnswerHint)
}