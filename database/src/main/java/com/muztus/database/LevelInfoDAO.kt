package com.muztus.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muztus.database.LevelTable.LEVEL_TABLE_LEVEL_INDEX
import com.muztus.database.LevelTable.LEVEL_TABLE_NAME
import com.muztus.database.LevelTable.LEVEL_TABLE_PREMIA_INDEX
import kotlinx.coroutines.flow.Flow


@Dao
interface LevelInfoDAO {

    @Query("SELECT * FROM $LEVEL_TABLE_NAME WHERE $LEVEL_TABLE_PREMIA_INDEX LIKE :premiaIndex AND $LEVEL_TABLE_LEVEL_INDEX LIKE :levelIndex LIMIT 1")
    suspend fun findLevel(premiaIndex: Int, levelIndex: Int): LevelInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(users: LevelInfoEntity)

    @Query("SELECT * FROM $LEVEL_TABLE_NAME WHERE $LEVEL_TABLE_PREMIA_INDEX LIKE :premiaIndex")
    suspend fun getPremiaLevels(premiaIndex: Int): List<LevelInfoEntity>

    @Query("SELECT * FROM $LEVEL_TABLE_NAME")
    fun getAllLevels(): Flow<List<LevelInfoEntity>>

}