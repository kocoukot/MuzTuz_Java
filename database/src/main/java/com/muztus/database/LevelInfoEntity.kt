package com.muztus.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.muztus.database.LevelTable.LEVEL_TABLE_HINT_ANSWER
import com.muztus.database.LevelTable.LEVEL_TABLE_HINT_LETTERS_AMOUNT
import com.muztus.database.LevelTable.LEVEL_TABLE_HINT_ONE_LETTER
import com.muztus.database.LevelTable.LEVEL_TABLE_HINT_SONG
import com.muztus.database.LevelTable.LEVEL_TABLE_LEVEL_DURATION
import com.muztus.database.LevelTable.LEVEL_TABLE_LEVEL_INDEX
import com.muztus.database.LevelTable.LEVEL_TABLE_NAME
import com.muztus.database.LevelTable.LEVEL_TABLE_PREMIA_INDEX


@Entity(
    tableName = LEVEL_TABLE_NAME,
    primaryKeys = [LEVEL_TABLE_PREMIA_INDEX, LEVEL_TABLE_LEVEL_INDEX]
)
data class LevelInfoEntity(
    @ColumnInfo(name = LEVEL_TABLE_PREMIA_INDEX) val premiaIndex: Int,
    @ColumnInfo(name = LEVEL_TABLE_LEVEL_INDEX) val levelIndex: Int,
    @ColumnInfo(name = LEVEL_TABLE_LEVEL_DURATION) val levelDuration: Long,

    @ColumnInfo(name = LEVEL_TABLE_HINT_LETTERS_AMOUNT) val isLettersAmountUsed: Boolean = false,
    @ColumnInfo(name = LEVEL_TABLE_HINT_ONE_LETTER) val selectedLetterIndex: Int = -1,
    @ColumnInfo(name = LEVEL_TABLE_HINT_SONG) val isSongOpened: Boolean = false,
    @ColumnInfo(name = LEVEL_TABLE_HINT_ANSWER) val isAnswerUsed: Boolean = false,

    @ColumnInfo(name = "is_solved") val isSolved: Boolean,
)


