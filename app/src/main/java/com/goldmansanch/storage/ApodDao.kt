package com.goldmansanch.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import com.goldmansanch.storage.data.Apod
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao
{

    @Query("SELECT * from apod_data")
    fun getFavouriteApod(): List<Apod>

    @Query("SELECT * from apod_data")
    fun getFavouriteApodLiveData(): LiveData<List<Apod>>

    @Query("SELECT COUNT() FROM apod_data WHERE date = :date")
    fun checkIfAPODAvailableNyDate(date: String): Int

    @Query("SELECT * FROM apod_data WHERE date = :date")
    fun getAPODByDate(date: String): Apod?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(apod: Apod): Long

    @Delete
    fun delete(apod: Apod): Int


}
