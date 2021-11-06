package com.task.mathongo.ui.marks.models

import android.os.Parcelable
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "testscore")
data class TestScore(

    val __v: Int,
    @PrimaryKey
    val _id: String,
    val createdAt: String,
    val email: String,
    val exam: String,
    val testDate: String,
    val testName: String,
    val testSeries: String,
    val updatedAt: String,

    val scores: Scores

):Parcelable{
    constructor():this(0,"","","","","","","",
    "",Scores(null,null,null))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TestScore) return false

        if (__v != other.__v) return false
        if (_id != other._id) return false
        if (createdAt != other.createdAt) return false
        if (email != other.email) return false
        if (exam != other.exam) return false
        if (testDate != other.testDate) return false
        if (testName != other.testName) return false
        if (testSeries != other.testSeries) return false
        if (updatedAt != other.updatedAt) return false
        if (scores != other.scores) return false

        return true
    }

    override fun hashCode(): Int {
        var result = __v
        result = 31 * result + _id.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + exam.hashCode()
        result = 31 * result + testDate.hashCode()
        result = 31 * result + testName.hashCode()
        result = 31 * result + testSeries.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + scores.hashCode()
        return result
    }

    fun showDropDown(view : CardView){
        if(view.isVisible){
            view.visibility = View.GONE
        }else{
            view.visibility = View.VISIBLE
        }

    }
}