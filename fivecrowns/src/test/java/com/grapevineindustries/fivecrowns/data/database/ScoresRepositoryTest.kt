package com.grapevineindustries.fivecrowns.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScoresRepositoryTest {

    private lateinit var database: ScoreDatabase
    private lateinit var scoreDao: ScoreDao
    private lateinit var repository: ScoresRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ScoreDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        scoreDao = database.scoreDao()
        repository = ScoresRepository(scoreDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `when getHighScore then flowEmitsHighScore`() = runTest {
        val highScore = Score(id = 1, playerName = "Player 1", score = 100)
        repository.insertScore(highScore)
        repository.insertScore(Score(id = 2, playerName = "Player 2", score = 50))


        repository.getHighScore().test {
            assertEquals(highScore, awaitItem())
        }
    }

    @Test
    fun `when getLowScore then flowEmitsLowScore`() = runTest {
        val lowScore = Score(id = 1, playerName = "Player 1", score = 10)
        repository.insertScore(lowScore)
        repository.insertScore(Score(id = 2, playerName = "Player 2", score = 50))

        repository.getLowScore().test {
            assertEquals(lowScore, awaitItem())
        }
    }

    @Test
    fun `when insertScore then scoreIsInDb`() = runTest {
        val newScore = Score(id = 3, playerName = "Player 3", score = 50)
        repository.insertScore(newScore)

        repository.getLowScore().test {
            assertEquals(newScore, awaitItem())
        }
    }
}

