package com.muztus.level_select_feature.model

sealed class LevelSelectActions {
    abstract fun handle(action: LevelActions)
    data class SelectLevel(private val selectedLevel: Int) : LevelSelectActions() {
        override fun handle(action: LevelActions) = action.selectLevel(selectedLevel)
    }
}

interface LevelActions {
    fun selectLevel(selectedLevel: Int)
}
