package com.task.mathongo.room

import android.content.Context
import androidx.room.*
import com.task.mathongo.ui.marks.models.Scores
import com.task.mathongo.ui.marks.models.TestScore

@TypeConverters(Converters::class)
@Database(entities = [TestScore::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    companion object{

        val DATABASE_NAME = "testscores"
        var databaseInstance : MyDatabase ?=null

        fun getDatabseInstance(context: Context):MyDatabase{
            if(databaseInstance ==null){
                synchronized(MyDatabase::class){
                    databaseInstance = Room.databaseBuilder(
                        context.applicationContext, MyDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return databaseInstance!!
        }


    }

    abstract fun testScoreDao(): TestScoreDao



}