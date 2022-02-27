package com.goldmansanch.di

import android.content.Context
import com.goldmansanch.network.NASAApi
import com.goldmansanch.util.Keys
import com.goldmansanch.util.NetworkUtil
import com.golmansanch.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

/**
 * Created by Abhishek on 27,February,2022 For GolmanSanch
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule
{

    @Provides
    @Singleton
    fun provideNASAApi(retrofit: Retrofit): NASAApi = retrofit.create(NASAApi::class.java)


    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        @BaseURL baseURL: String
    ): Retrofit
    {
        return retrofitBuilder.baseUrl(baseURL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder
    {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Provides
    fun provideGson(): Gson
    {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient
    {

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG)
        {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        return httpClient.build()
    }

    @Provides
    @BaseURL
    fun provideBaseURL(keys: Keys): String
    {
        return keys.baseURL()
    }

    @Provides
    fun provideNetworkUtil(@ApplicationContext context: Context)= NetworkUtil(context)
}
