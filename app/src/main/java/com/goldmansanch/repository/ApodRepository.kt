package com.goldmansanch.repository

import androidx.lifecycle.LiveData
import com.goldmansanch.data.APODItem
import com.goldmansanch.data.Response
import com.goldmansanch.storage.data.Apod
import kotlinx.coroutines.flow.Flow

/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 *
 */
interface ApodRepository
{

    suspend fun getAPODByDate(date: String, callback: (result: Response<APODItem>) -> Unit)

    suspend fun getFavAPODList(): LiveData<List<APODItem>>

    suspend fun markFavourite(apod: APODItem, isFev: Boolean): Long

    suspend fun deleteFav(apod: APODItem): Int

    suspend fun getLastCachedAPODItem(): APODItem?

}