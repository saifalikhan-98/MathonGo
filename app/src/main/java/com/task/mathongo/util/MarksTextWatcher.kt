package com.task.mathongo.util

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText

class MarksTextWatcher(val editText: EditText) : TextWatcher{
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(count>0){
            val firstNumber = s.toString()
            Log.d("xfirstNumber",s.toString()+" "+firstNumber.toString())
            if(firstNumber.length > 1 &&firstNumber.toInt()== 10){

                val maxLength = 3
                val fArray = arrayOfNulls<InputFilter>(1)
                fArray[0] = LengthFilter(maxLength)
                editText.filters = fArray
            }
        }

    }

    override fun afterTextChanged(s: Editable?) {
    }
}