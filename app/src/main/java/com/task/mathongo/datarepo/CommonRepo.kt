package com.task.mathongo.datarepo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.task.mathongo.BaseClass
import com.task.mathongo.network.ApiService
import com.task.mathongo.ui.marks.adapters.MyPagingScource
import com.task.mathongo.ui.marks.models.AddTestRequest
import com.task.mathongo.ui.marks.models.TestScore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject
import kotlin.collections.HashMap


class CommonRepo @Inject constructor( private val apiService: ApiService,private val app:BaseClass) {

    suspend fun getTestScores(queryMap : HashMap<String,String>) = apiService.getTestScore(queryMap)

    suspend fun uploadTestScores(addTestRequest: AddTestRequest) = apiService.uploadTestScores(addTestRequest)

    suspend fun updateTestScore(id:String, scores: RequestBody) = apiService.updateTestScore(id,scores)

    suspend fun getTestScoreById(id:String) = apiService.getTestScoreById(id)

    suspend fun getTestSeries() = apiService.getTestSeries()

    suspend fun deleteTestScore(id: String) = apiService.deleteTestScore(id)



    fun getPagingData(): Flow<PagingData<TestScore>> {

        return Pager(

                config = PagingConfig(
                        pageSize = 5,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = {MyPagingScource(apiService,app)}
        ).flow

    }
}