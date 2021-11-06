package com.task.mathongo.di

import android.content.Context
import com.task.mathongo.BaseClass
import com.task.mathongo.datarepo.CommonRepo
import com.task.mathongo.network.ApiService
import com.task.mathongo.util.AppUtils
import com.task.mathongo.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app : Context) : BaseClass{

        return app as BaseClass
    }




    @Provides
    fun provideBaseUrl() = Constants.BASE_URL


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httploggers = HttpLoggingInterceptor()
        httploggers.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .addInterceptor(httploggers)
            .build()

        return client
    }

    @Singleton
    @Provides
    fun getCommonRepo(apiService: ApiService,app:BaseClass):CommonRepo = CommonRepo(apiService,app)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}