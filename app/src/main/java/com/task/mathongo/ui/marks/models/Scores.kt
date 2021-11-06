package com.task.mathongo.ui.marks.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scores(

    val Chemistry : Int?=null,
    val Mathematics: Int?=null,
    val Physics: Int?=null
) : Parcelable{
    constructor():this(0,0,0)
}