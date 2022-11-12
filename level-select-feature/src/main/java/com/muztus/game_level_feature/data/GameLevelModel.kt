package com.muztus.game_level_feature.data

interface GameLevelModel {

    fun checkUserInput(userInput: String): Boolean = false

    fun onHintUse(hint: HintModel) = Unit

    fun hintsRow(): List<HintModel> = emptyList()

    fun getLevelImage(): Int = 0

    fun getLevelSongHint(): String = ""

    fun getLettersAmount(): String = ""


    data class Base(
        private val index: Int,
        private val correctAnswers: List<String>,
        private val levelHints: LevelHints,
        private val levelImage: Int,
        private val songName: String,
        private val isSolved: Boolean,
    ) : GameLevelModel {

        override fun checkUserInput(userInput: String): Boolean =
            correctAnswers.any { it.equals(userInput.trim().lowercase(), true) }

        override fun onHintUse(hint: HintModel) {
            println("selected hint $hint levelHints $levelHints")
            levelHints.getHintsList().find {
                it.getId(hint)
            }?.useHint()
        }

        override fun hintsRow(): List<HintModel> = levelHints.getHintsList()

        override fun getLevelImage(): Int = levelImage

        override fun getLevelSongHint(): String = songName

        override fun getLettersAmount(): String = correctAnswers.first().map {
            if (it.equals(' ', true)) " " else "_"
        }.joinToString(" ")

    }

    class Empty : GameLevelModel
}