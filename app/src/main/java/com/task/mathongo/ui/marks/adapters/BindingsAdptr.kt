package com.task.mathongo.ui.marks.adapters

import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.lang.Exception
import java.text.SimpleDateFormat

@BindingAdapter("datebinder")
fun formatdate(view: TextView, date: String){


    val datetextView = view

    val fromServer = SimpleDateFormat("yyyy-MM-dd")
    val myFormat = SimpleDateFormat("yyyy MMM dd")

    val datetobeformatted = fromServer.parse(date)
    val outputDateStr = myFormat.format(datetobeformatted)

    Log.d("date",outputDateStr)
    datetextView.text =outputDateStr
}

@BindingAdapter(value =["physicsmarks","chemistryMarks","math"], requireAll = false)
fun getTotal(view: TextView,physicsmarks :Int, chemistryMarks:Int, math:Int){


    val total = physicsmarks + chemistryMarks+ math

    view.text = "${total}/300"

}
@BindingAdapter("updatedatebinder")
fun updateformatdate(view: TextView, date: String){

    val datetextView = view
    try {


        val fromServer = SimpleDateFormat("yyyy-MM-dd")
        val myFormat = SimpleDateFormat("MM/dd/yyyy")

        val datetobeformatted = fromServer.parse(date)
        val outputDateStr = myFormat.format(datetobeformatted)

        Log.d("date",outputDateStr)
        datetextView.apply {
            text =outputDateStr
            setTextColor(Color.BLACK)
        }
    }catch (e :Exception){
        datetextView.text = "MM/DD/YYYY"
    }

}
