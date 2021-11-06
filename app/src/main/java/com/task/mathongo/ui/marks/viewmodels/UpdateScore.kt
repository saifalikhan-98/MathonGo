package com.task.mathongo.ui.marks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.mathongo.datarepo.CommonRepo
import com.task.mathongo.network.Resource
import com.task.mathongo.ui.marks.models.AddTestResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UpdateScore@Inject() constructor(private val commonRepo: CommonRepo): ViewModel(){


    val updateStatus : MutableLiveData<Resource<AddTestResponse>> = MutableLiveData()


    fun updateScore(id:String, scores: RequestBody){

        CoroutineScope(Dispatchers.IO).launch {

            try{
                updateStatus.postValue(Resource.Loading())
                val response  =commonRepo.updateTestScore(id,scores)
                updateStatus.postValue(HandleDeleteResponse(response))

            }catch (e: Exception){
                e.printStackTrace()
                updateStatus.postValue(Resource.Error("Error processing request"))
            }
        }
    }

    private fun HandleDeleteResponse(response: Response<AddTestResponse>): Resource<AddTestResponse>? {
        if(response.isSuccessful){

            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


}