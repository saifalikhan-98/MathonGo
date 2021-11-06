package com.task.mathongo.ui.marks.viewmodels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.task.mathongo.datarepo.CommonRepo
import com.task.mathongo.ui.marks.models.TestScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

@HiltViewModel
class PagingViewModel @Inject() constructor(private val commonRepo: CommonRepo): ViewModel(){






    fun getData(): Flow<PagingData<TestScore>> = commonRepo.getPagingData().cachedIn(viewModelScope)






}