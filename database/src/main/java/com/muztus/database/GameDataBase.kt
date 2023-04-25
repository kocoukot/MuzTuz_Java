package com.muztus.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [LevelInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gameLevelInfo(): LevelInfoDAO
}
