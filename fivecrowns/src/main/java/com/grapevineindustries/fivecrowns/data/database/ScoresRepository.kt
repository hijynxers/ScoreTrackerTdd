package com.grapevineindustries.fivecrowns.data.database

import kotlinx.coroutines.flow.Flow

class ScoresRepository(private val scoreDao: ScoreDao) {

    fun getHighScore(): Flow<Score?> = scoreDao.getHighScore()

    fun getLowScore(): Flow<Score?> = scoreDao.getLowScore()

    suspend fun insertScore(score: Score) {
        scoreDao.insert(score)
    }
}

