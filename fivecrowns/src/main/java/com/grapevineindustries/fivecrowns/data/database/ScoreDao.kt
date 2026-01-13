package com.grapevineindustries.fivecrowns.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun insert(score: Score)

    @Query("SELECT * FROM scores ORDER BY score ASC LIMIT 1")
    fun getLowScore(): Flow<Score?>

    @Query("SELECT * FROM scores ORDER BY score DESC LIMIT 1")
    fun getHighScore(): Flow<Score?>
}

