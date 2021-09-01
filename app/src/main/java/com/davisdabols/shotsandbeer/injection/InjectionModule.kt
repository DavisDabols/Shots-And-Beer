package com.davisdabols.shotsandbeer.injection

import android.content.Context
import androidx.room.Room
import com.davisdabols.shotsandbeer.repository.GameRepository
import com.davisdabols.shotsandbeer.repository.cache.GameDatabase
import com.davisdabols.shotsandbeer.common.HIGH_SCORE_DATABASE
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InjectionModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideNoteDataBase() = Room
        .databaseBuilder(context, GameDatabase::class.java, HIGH_SCORE_DATABASE)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNoteRepository(database: GameDatabase) = GameRepository(database.gameDao())
}
