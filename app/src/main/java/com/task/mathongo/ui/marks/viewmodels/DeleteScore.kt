package com.task.mathongo.ui.marks.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.mathongo.datarepo.CommonRepo
import com.task.mathongo.network.Resource
import com.task.mathongo.ui.marks.models.DeleteTestScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DeleteScore @Inject() constructor(private val commonRepo: CommonRepo):ViewModel(){


    val deleteStatus : MutableLiveData<Resource<DeleteTestScore>> = MutableLiveData()


    fun deleteScore(id:String){

        CoroutineScope(Dispatchers.IO).launch {

            try{
                deleteStatus.postValue(Resource.Loading())
                val response  =commonRepo.deleteTestScore(id)
                deleteStatus.postValue(HandleDeleteResponse(response))

            }catch (e:Exception){
                e.printStackTrace()
                deleteStatus.postValue(Resource.Error("Error processing request"))
            }
        }
    }

    private fun HandleDeleteResponse(response: Response<DeleteTestScore>): Resource<DeleteTestScore> {
        if(response.isSuccessful){

            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}