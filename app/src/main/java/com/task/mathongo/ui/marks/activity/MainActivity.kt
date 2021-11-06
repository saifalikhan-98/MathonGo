package com.task.mathongo.ui.marks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.task.mathongo.R
import com.task.mathongo.util.AppUtils
import com.task.mathongo.util.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}