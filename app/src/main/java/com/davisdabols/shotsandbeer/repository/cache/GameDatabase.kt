package com.davisdabols.shotsandbeer.repository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davisdabols.shotsandbeer.repository.models.HighScoreModel

@Database(entities = [HighScoreModel::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

}
