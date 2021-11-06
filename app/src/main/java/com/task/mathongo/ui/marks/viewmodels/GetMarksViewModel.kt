package com.task.mathongo.ui.marks.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.mathongo.BaseClass
import com.task.mathongo.datarepo.CommonRepo
import com.task.mathongo.network.Resource
import com.task.mathongo.room.MyDatabase
import com.task.mathongo.room.TestScoreDao
import com.task.mathongo.ui.marks.models.TestScore
import com.task.mathongo.ui.marks.models.TestScoresWithLimit
import com.task.mathongo.util.AppUtils
import com.task.mathongo.util.PreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class GetMarksViewModel @Inject constructor(private val app: BaseClass) :ViewModel() {

    val marksList : MutableLiveData<List<TestScore>> = MutableLiveData()
    private lateinit var testDao :TestScoreDao




}