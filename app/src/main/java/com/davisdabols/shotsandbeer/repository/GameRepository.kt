package com.davisdabols.shotsandbeer.repository

import com.davisdabols.shotsandbeer.repository.models.HighScoreModel
import com.davisdabols.shotsandbeer.repository.cache.GameDao

class GameRepository(private val gameDao: GameDao) {

    val highScores = gameDao.getHighScores()

    fun insertHighScore(highScoreModel: HighScoreModel) = gameDao.insertHighScore(highScoreModel)

}
