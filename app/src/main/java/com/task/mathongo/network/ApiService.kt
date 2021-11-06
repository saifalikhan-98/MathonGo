package com.task.mathongo.network

import com.task.mathongo.ui.marks.models.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import kotlin.collections.HashMap

interface ApiService {

    @GET("test-series")
    suspend fun getTestSeries():Response<TestSeries>


    @POST("test-scores")
    suspend fun uploadTestScores(@Body addTestRequest: AddTestRequest) : Response<AddTestResponse>


    @GET("test-scores")
    suspend fun getTestScore(@QueryMap params : HashMap<String,String>) : Response<TestScoresWithLimit>


    @GET("test-scores/{id}")
    suspend fun getTestScoreById(@Path("id") id :String) : Response<TestScoresWithLimit>


    @PATCH("test-scores/{id}")
    suspend fun updateTestScore(@Path("id") id:String, @Body scores: RequestBody) : Response<AddTestResponse>



    @DELETE("test-scores/{id}")
    suspend fun deleteTestScore(@Path("id") id :String) : Response<DeleteTestScore>


}