package com.muztus.domain_layer.model

interface GameLevelModel {

    fun checkUserInput(userInput: String): Boolean = false

    fun hintsRow(): List<HintModel> = emptyList()

    fun getLevelImage(): Int = 0

    fun getLevelSongHint(): String = ""
    fun getLettersAmount(): String = ""
    fun getCorrectAnswer(): String = ""


    fun lettersAmountHintUse(hintUse: HintUse) = Unit
    fun onOneLetterHintUse(hintUse: HintUse, letterIndex: Int) = Unit
    fun songHintUse(hintUse: HintUse) = Unit

    fun isSolved(): Boolean = false

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

        override fun getLevelImage(): Int = levelImage

        override fun getLevelSongHint(): String = songName

        override fun getLettersAmount(): String =
            if (levelHints.letterAmountHint.isEnabled() || levelHints.oneLetterHint.isEnabled()) {
                val answer = correctAnswers.first()
                val choosenLetterIndex = levelHints.oneLetterHint.selectedLetters
                val choosenLetter =
                    if (choosenLetterIndex >= 0) answer[choosenLetterIndex].uppercase() else ""
                correctAnswers.first().mapIndexed { index, char ->
                    if (choosenLetterIndex == index) choosenLetter else {
                        if (char.equals(' ', true)) " " else "_"
                    }
                }.joinToString(" ")
            } else ""

        override fun getCorrectAnswer(): String = correctAnswers.first()


        override fun lettersAmountHintUse(hintUse: HintUse) {
            levelHints.letterAmountHint.onHintUsed(hintUse)
        }

        override fun onOneLetterHintUse(hintUse: HintUse, letterIndex: Int) {
            levelHints.letterAmountHint.onHintUsed()
            levelHints.oneLetterHint.onHintUsed(hintUse)
            levelHints.oneLetterHint = levelHints.oneLetterHint.copy(selectedLetters = letterIndex)
        }

        override fun songHintUse(hintUse: HintUse) {
            levelHints.songHint.onHintUsed(hintUse)
        }

        override fun isSolved(): Boolean = isSolved

    }


    object Empty : GameLevelModel {
//        override fun toEntity(): LevelInfoEntity? = null
    }
}