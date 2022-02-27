package com.goldmansanch.network

import com.goldmansanch.storage.data.Apod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 *
 */
interface NASAApi
{

    @GET("/planetary/apod")
    fun getAPODData(
        @Query(value = "api_key") apiKey: String,
        @Query("date") date: String
    ): Call<Apod>

}