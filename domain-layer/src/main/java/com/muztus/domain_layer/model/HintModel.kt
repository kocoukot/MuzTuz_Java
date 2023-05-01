package com.muztus.domain_layer.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.muztus.core.theme.MTTheme
import com.muztus.domain_layer.R


interface HintModel {

    var isUsed: Boolean

    fun canUseHint(totalAmount: Int): Boolean

    fun onHintUsed(hintUse: HintUse? = null)

    fun useHintTest(use: HintUse)

    @Composable
    fun HintImage(modifier: Modifier)

    fun hintCost(): Int

    fun hintName(): Int


    @Composable
    fun HintAnswer(modifier: Modifier, hintText: String)

    abstract class Abstract(
        private val hintCost: Int,
        private val hintName: Int,
        private val hintImage: Int,
        private val hintImageUsed: Int,
    ) : HintModel {

        @Composable
        override fun HintImage(modifier: Modifier) {
            Image(
                contentScale = ContentScale.FillWidth,
                modifier = modifier.then(
                    if (isUsed)
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            enabled = isUsed,
                        ) {}
                    else modifier
                ),
                painter = painterResource(id = if (isUsed) hintImageUsed else hintImage),
                contentDescription = null
            )
        }

        override fun canUseHint(totalAmount: Int): Boolean = totalAmount >= hintCost

        override fun hintCost(): Int = hintCost

        override fun hintName(): Int = hintName

        override fun onHintUsed(hintUse: HintUse?) {
            hintUse?.changeCoinsAmount(hintCost)
            isUsed = true
        }
    }

    data class LetterAmountHint(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintCost = 100,
        hintName = R.string.hint_name_letters_amount,
        hintImage = R.drawable.podskazka_kolichestvo_bukv,
        hintImageUsed = R.drawable.podskazka_kolichestvo_bukv_zakrita,
    ) {
        override fun useHintTest(use: HintUse) = use.lettersAmount()

        @Composable
        override fun HintAnswer(modifier: Modifier, hintText: String) {
            val answer = hintText.map { char ->
                if (char.equals(' ', true)) " " else "_"
            }.joinToString(" ")

            if (isUsed) {
                Text(
                    color = MTTheme.colors.buttonPressed,
                    fontSize = 28.sp,
                    text = answer,
                    modifier = modifier
                )
            }
        }

    }

    data class OneLetterHint(
        override var isUsed: Boolean = false,
        var selectedLetterIndex: Int = -1
    ) : Abstract(
        hintCost = 200,
        hintName = R.string.hint_name_one_letter,
        hintImage = R.drawable.podskazka_lubay_bukva,
        hintImageUsed = R.drawable.podskazka_lubay_bukva_zakrita
    ) {

        override fun useHintTest(use: HintUse) = use.showLetterSelect()

        @Composable
        override fun HintAnswer(modifier: Modifier, hintText: String) {
            val choosenLetter =
                if (selectedLetterIndex >= 0) hintText[selectedLetterIndex].uppercase() else ""

            val answer = hintText.mapIndexed { index, char ->
                if (selectedLetterIndex == index) choosenLetter else {
                    if (char.equals(' ', true)) " " else "_"
                }
            }.joinToString(" ")

            if (isUsed) {
                Text(
                    color = MTTheme.colors.buttonPressed,
                    fontSize = 28.sp,
                    text = answer,
                    modifier = modifier
                )
            }
        }

    }

    data class SongHint(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintCost = 400,
        hintName = R.string.hint_name_song_name,
        hintImage = R.drawable.podskazka_albom_pesny,
        hintImageUsed = R.drawable.podskazka_albom_pesny_zakrita
    ) {
        override fun useHintTest(use: HintUse) = use.songHint()


        @Composable
        override fun HintAnswer(modifier: Modifier, hintText: String) {
            if (isUsed) Text(
                color = MTTheme.colors.white,
                fontSize = 16.sp,
                text = hintText,
                modifier = modifier
            )
        }

    }

    data class CorrectAnswer(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintCost = 500,
        hintName = R.string.hint_name_show_answer,
        hintImage = R.drawable.podskazka_podskazok,
        hintImageUsed = R.drawable.podskazka_podskazok_zakrita
    ) {
        override fun useHintTest(use: HintUse) = use.answerHint()


        @Composable
        override fun HintAnswer(modifier: Modifier, hintText: String) {

        }


    }

}

interface HintUse {
    fun lettersAmount()
    fun showLetterSelect()
    fun songHint()
    fun answerHint()
    fun changeCoinsAmount(hintPrice: Int)

}


data class LevelHints(
    val letterAmountHint: HintModel.LetterAmountHint = HintModel.LetterAmountHint(),
    var oneLetterHint: HintModel.OneLetterHint = HintModel.OneLetterHint(),
    val songHint: HintModel.Abstract = HintModel.SongHint(),
    var correctAnswerHint: HintModel.Abstract = HintModel.CorrectAnswer(),
) {
    fun getHintsList() = listOf(letterAmountHint, oneLetterHint, songHint, correctAnswerHint)
}