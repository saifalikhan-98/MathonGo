package com.task.mathongo.ui.marks.adapters

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.task.mathongo.BaseClass
import com.task.mathongo.network.ApiService
import com.task.mathongo.room.MyDatabase
import com.task.mathongo.ui.marks.models.TestScore
import com.task.mathongo.util.AppUtils
import com.task.mathongo.util.PreferenceHelper
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject


class MyPagingScource @Inject() constructor(
        private val apiService: ApiService,
        private val app :BaseClass


):PagingSource<Int, TestScore>() {
    private val PAGEINDEX = 0
    private lateinit var testScore: List<TestScore>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TestScore> {
        val position = params.key?: PAGEINDEX
        testScore =ArrayList()

        return try {

            Log.d("pagingData","From pagingSource Paging Data Called")
            val queryMap :HashMap<String, String> = HashMap()
            queryMap["email"] = PreferenceHelper.getEmailId(app.applicationContext).toString()
            queryMap["page"] = position.toString()
            queryMap["limit"] = "5"

            if(AppUtils.isNetworkAvailable(app.applicationContext)){
                val response = apiService.getTestScore(queryMap)
                val testScore :List<TestScore> = response.body()!!.testScores
                setDataForRoom(testScore)
                LoadResult.Page(
                        data =testScore,
                        prevKey = if(position == PAGEINDEX) null else position-1,
                        nextKey = if(testScore.isEmpty())null else position +1)
            }else{

                CoroutineScope(Dispatchers.IO).async {
                    getDataFromRoom()
                }.await()

                Log.d("xRoom",testScore.toString())

                LoadResult.Page(
                        data =testScore,
                        prevKey = if(position == PAGEINDEX) null else position-1,
                        nextKey = if(testScore.isEmpty())null else position +1)
            }

        }catch(e : IOException){
            AppUtils.showToastMsg(app.applicationContext,"Error loading data")
            e.printStackTrace()
            LoadResult.Error(e)

        }catch(e :HttpException){
            AppUtils.showToastMsg(app.applicationContext,"Error loading data")
            e.printStackTrace()
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, TestScore>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }


    private fun setDataForRoom(testScore: List<TestScore>) {
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val db = MyDatabase.getDatabseInstance(app.applicationContext)
                val testDao = db.testScoreDao()
                for(i in 0 until testScore.size){
                    val rowId = testDao.saveScores(testScore.get(i))
                    Log.d("xRoom", rowId.toString())
                }





            }catch (e : Exception){

                Log.d("xRoom","error ${e.localizedMessage}")
            }
        }


    }


     fun  getDataFromRoom() {

           try{
               val db = MyDatabase.getDatabseInstance(app.applicationContext)
               val testDao = db.testScoreDao()
               testScore= testDao.getScoresFromDB()

               Log.d("xRoom",testScore.toString())

           }catch (e :Exception ){
               Log.d("xRoom","error ${e.localizedMessage}")


           }




    }


}