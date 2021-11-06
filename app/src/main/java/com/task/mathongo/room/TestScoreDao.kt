package com.task.mathongo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.mathongo.ui.marks.models.TestScore

@Dao
interface TestScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun saveScores(testScore: TestScore):Long


    @Query("SELECT * FROM testscore")
    fun getScoresFromDB():List<TestScore>
}