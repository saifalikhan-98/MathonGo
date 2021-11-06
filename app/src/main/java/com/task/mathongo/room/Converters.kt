package com.task.mathongo.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.task.mathongo.ui.marks.models.Scores

class Converters {





    @TypeConverter
    fun fromScore(value : Scores):String?{
        return Gson().toJson(value)
    }
    @TypeConverter
    fun toScores(value:String):Scores{
        return Gson().fromJson(value,Scores::class.java)
    }
}