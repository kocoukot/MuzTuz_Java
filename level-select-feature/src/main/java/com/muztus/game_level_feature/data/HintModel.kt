package com.muztus.game_level_feature.data

import com.muztus.level_select_feature.R

interface HintModel {

    var isUsed: Boolean

    fun canUseHint(totalAmount: Int): Boolean

    fun getId(id: HintModel): Boolean

    fun useHint()

    fun hintImage(): Int

    fun hintCost(): Int

    fun hintName(): Int

    abstract class Abstract(
        private val hintId: Int,
        private val hintCost: Int,
        private val hintName: Int,
        private val hintImage: Int,
    ) : HintModel {

        override fun hintImage(): Int = hintImage

        override fun canUseHint(totalAmount: Int): Boolean = totalAmount >= hintCost

        override fun hintCost(): Int = hintCost

        override fun hintName(): Int = hintName

        override fun getId(id: HintModel): Boolean = hintId == (id as Abstract).hintId

        override fun useHint() {
            isUsed = true
        }
    }

    data class LetterAmountHint(
        override var isUsed: Boolean = false
    ) : Abstract(
        hintId = 1,
        hintCost = 100,
        hintName = R.string.hint_name_letters_amount,
        hintImage = R.drawable.podskazka_kolichestvo_bukv
    )

    data class OneLetterHint(
        override var isUsed: Boolean = false,
        private val selectedLetters: Set<Int> = setOf()
    ) : Abstract(
        hintId = 2,
        hintCost = 200,
        hintName = R.string.hint_name_one_letter,
        hintImage = R.drawable.podskazka_lubay_bukva
    )

    data class SongHint(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintId = 3,
        hintCost = 400,
        hintName = R.string.hint_name_song_name,
        hintImage = R.drawable.podskazka_albom_pesny
    )

    data class CorrectAnswer(
        override var isUsed: Boolean = false,
    ) : Abstract(
        hintId = 4,
        hintCost = 500,
        hintName = R.string.hint_name_show_answer,
        hintImage = R.drawable.podskazka_podskazok
    )

}


data class LevelHints(
    val letterAmountHint: HintModel = HintModel.LetterAmountHint(),
    val oneLetterHint: HintModel = HintModel.OneLetterHint(),
    val songHint: HintModel = HintModel.SongHint(),
    val correctAnswerHint: HintModel = HintModel.CorrectAnswer(),
) {
    fun getHintsList() = listOf(letterAmountHint, oneLetterHint, songHint, correctAnswerHint)
}