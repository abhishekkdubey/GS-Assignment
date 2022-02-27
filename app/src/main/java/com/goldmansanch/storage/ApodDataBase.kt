package com.goldmansanch.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goldmansanch.storage.data.Apod

/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 *
 */
@Database(entities = [Apod::class], version = 1, exportSchema = false)
abstract class ApodDataBase: RoomDatabase() {
    abstract fun apodDao() : ApodDao
}