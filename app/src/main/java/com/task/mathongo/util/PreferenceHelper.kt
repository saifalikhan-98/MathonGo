package com.task.mathongo.util

import android.content.Context
import android.content.SharedPreferences
import com.task.mathongo.util.Constants.Companion.EMAIL

class PreferenceHelper {

    companion object{

        private fun preferenceHelper(context: Context):SharedPreferences = context.getSharedPreferences("default",0)


        fun setEmailId(context: Context,email:String){

            preferenceHelper(context)
                .edit()
                .putString(EMAIL,email)
                .apply()

        }

        fun getEmailId(context: Context):String? = preferenceHelper(context).getString(EMAIL,null)


    }
}