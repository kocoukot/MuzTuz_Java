package com.muztus.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.muztus.database.LevelTable.LEVEL_TABLE_LEVEL_INDEX
import com.muztus.database.LevelTable.LEVEL_TABLE_NAME
import com.muztus.database.LevelTable.LEVEL_TABLE_PREMIA_INDEX

@Entity(
    tableName = LEVEL_TABLE_NAME,
    primaryKeys = [LEVEL_TABLE_PREMIA_INDEX, LEVEL_TABLE_LEVEL_INDEX]
)
data class LevelInfoEntity(
//    @PrimaryKey val uid: String,
    @ColumnInfo(name = LEVEL_TABLE_PREMIA_INDEX) val premiaIndex: Int,
    @ColumnInfo(name = LEVEL_TABLE_LEVEL_INDEX) val levelIndex: Int,
    @ColumnInfo(name = "is_solved") val isSolved: Boolean,
)


