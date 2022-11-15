package com.muztus.domain_layer.model

import com.muztus.domain_layer.R


interface HintModel {

    var isUsed: Boolean

    fun canUseHint(totalAmount: Int): Boolean

    fun getId(id: HintModel): Boolean

    fun onHintUsed(hintUse: HintUse? = null)

    fun useHintTest(use: HintUse)

    fun hintImage(): Int

    fun hintCost(): Int

    fun hintName(): Int


    fun isEnabled(): Boolean

    abstract class Abstract(
        private val hintId: Int,
        private val hintCost: Int,
        private val hintName: Int,
        private val hintImage: Int,
        private val hintImageUsed: Int,
    ) : HintModel {

        override fun isEnabled(): Boolean = isUsed

        override fun hintImage(): Int = if (isUsed) hintImageUsed else hintImage

        override fun canUseHint(totalAmount: Int): Boolean = totalAmount >= hintCost

        override fun hintCost(): Int = hintCost

        override fun hintName(): Int = hintName

        override fun getId(id: HintModel): Boolean = hintId == (id as Abstract).hintId

        override fun onHintUsed(hintUse: HintUse?) {
            hintUse?.changeCoinsAmount(hintCost)
            isUsed = true
        }
    }

    data class LetterAmountHint(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintId = 1,
        hintCost = 100,
        hintName = R.string.hint_name_letters_amount,
        hintImage = R.drawable.podskazka_kolichestvo_bukv,
        hintImageUsed = R.drawable.podskazka_kolichestvo_bukv_zakrita,
    ) {
        override fun useHintTest(use: HintUse) = use.lettersAmount()
    }

    data class OneLetterHint(
        override var isUsed: Boolean = false,
        var selectedLetters: Int = -1
    ) : Abstract(
        hintId = 2,
        hintCost = 200,
        hintName = R.string.hint_name_one_letter,
        hintImage = R.drawable.podskazka_lubay_bukva,
        hintImageUsed = R.drawable.podskazka_lubay_bukva_zakrita
    ) {

        override fun useHintTest(use: HintUse) = use.showLetterSelect()
    }

    data class SongHint(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintId = 3,
        hintCost = 400,
        hintName = R.string.hint_name_song_name,
        hintImage = R.drawable.podskazka_albom_pesny,
        hintImageUsed = R.drawable.podskazka_albom_pesny_zakrita
    ) {
        override fun useHintTest(use: HintUse) = use.songHint()
    }

    data class CorrectAnswer(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintId = 4,
        hintCost = 500,
        hintName = R.string.hint_name_show_answer,
        hintImage = R.drawable.podskazka_podskazok,
        hintImageUsed = R.drawable.podskazka_podskazok_zakrita
    ) {
        override fun useHintTest(use: HintUse) = use.answerHint()

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