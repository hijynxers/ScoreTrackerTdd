package com.grapevineindustries.fivecrowns.data.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScoreDatabaseTest {

    @Test
    fun whenGetDatabaseCalledMultipleTimes_thenReturnsSameInstance() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database1 = ScoreDatabase.getDatabase(context)
        val database2 = ScoreDatabase.getDatabase(context)

        assertEquals(database1, database2)
    }

    @Test
    fun whenScoreDaoCalled_thenReturnsScoreDaoInstance() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = ScoreDatabase.getDatabase(context)
        val scoreDao = database.scoreDao()

        assertNotNull(scoreDao)
    }
}

