package com.task.mathongo.ui.marks.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.mathongo.datarepo.CommonRepo
import com.task.mathongo.network.Resource
import com.task.mathongo.ui.marks.models.AddTestRequest
import com.task.mathongo.ui.marks.models.AddTestResponse
import com.task.mathongo.ui.marks.models.TestScoresWithLimit
import com.task.mathongo.ui.marks.models.TestSeries
import com.task.mathongo.util.PreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UploadMarksViewModel @Inject() constructor(private val commonRepo: CommonRepo) : ViewModel() {

    val marksList : MutableLiveData<Resource<AddTestResponse>> = MutableLiveData()
    val testSeries : MutableLiveData<Resource<List<String>>> = MutableLiveData()

    init{
        getTestSeries()
    }

    fun uploadMarks(context: Context, addTestRequest: AddTestRequest){

        CoroutineScope(Dispatchers.IO).launch {

            try {

                marksList.postValue(Resource.Loading())
                val response = commonRepo.uploadTestScores(addTestRequest)
                marksList.postValue(HandleResponse(response,context))
            }catch (e : Exception){
                e.printStackTrace()
                marksList.postValue(Resource.Error("Error processing request"))
            }

        }

    }

    private fun HandleResponse(response: Response<AddTestResponse>, context: Context): Resource<AddTestResponse> {

        if(response.isSuccessful){

            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())

    }



    private fun getTestSeries(){
        CoroutineScope(Dispatchers.IO).launch {

            try {
                testSeries.postValue(Resource.Loading())
                val response = commonRepo.getTestSeries()
                testSeries.postValue(HandleTestSeriesRespone(response))


            }catch (e : Exception){
                testSeries.postValue(Resource.Error("Error processing request"))
            }

        }

    }

    private fun HandleTestSeriesRespone(response: Response<TestSeries>): Resource<List<String>>{

        if(response.isSuccessful){
            response.body()?.testSeries?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())

    }


}