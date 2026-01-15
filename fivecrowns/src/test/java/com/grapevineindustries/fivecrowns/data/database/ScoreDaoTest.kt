package com.grapevineindustries.fivecrowns.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScoreDaoTest {

    private lateinit var database: ScoreDatabase
    private lateinit var scoreDao: ScoreDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ScoreDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        scoreDao = database.scoreDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun whenInsertScore_thenScoreIsInDb() = runTest {
        val score = Score(playerName = "Player 1", score = 100)
        scoreDao.insert(score)

        scoreDao.getHighScore().test {
            assertEquals(score.copy(id = 1), awaitItem())
        }
    }

    @Test
    fun whenGetHighScore_andDbIsEmpty_thenReturnsNull() = runTest {
        scoreDao.getHighScore().test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun whenGetHighScore_thenReturnsHighestScore() = runTest {
        val highScore = Score(playerName = "Player 1", score = 100)
        val lowScore = Score(playerName = "Player 2", score = 10)
        scoreDao.insert(highScore)
        scoreDao.insert(lowScore)

        scoreDao.getHighScore().test {
            assertEquals(highScore.copy(id = 1), awaitItem())
        }
    }

    @Test
    fun whenGetLowScore_andDbIsEmpty_thenReturnsNull() = runTest {
        scoreDao.getLowScore().test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun whenGetLowScore_thenReturnsLowestScore() = runTest {
        val highScore = Score(playerName = "Player 1", score = 100)
        val lowScore = Score(playerName = "Player 2", score = 10)
        scoreDao.insert(highScore)
        scoreDao.insert(lowScore)

        scoreDao.getLowScore().test {
            assertEquals(lowScore.copy(id = 2), awaitItem())
        }
    }
}

