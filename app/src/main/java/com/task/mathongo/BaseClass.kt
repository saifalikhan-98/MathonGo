package com.task.mathongo

import android.app.Application
import com.task.mathongo.util.AppUtils
import com.task.mathongo.util.PreferenceHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseClass:Application() {

    override fun onCreate() {
        super.onCreate()



    }

}