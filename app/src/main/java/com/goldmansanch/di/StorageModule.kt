package com.goldmansanch.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.SharedPreferences
import com.goldmansanch.storage.ApodDao
import com.goldmansanch.storage.ApodDataBase
import com.goldmansanch.util.Constants


/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class StorageModule
{

    @Provides
    @Singleton
    fun provideApodDao(database: ApodDataBase): ApodDao
    {
        return database.apodDao()
    }

    @Provides
    @Singleton
    fun provideApodDataBase(@ApplicationContext appContext: Context): ApodDataBase
    {
        return Room.databaseBuilder(appContext, ApodDataBase::class.java, Constants.DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences
    {
        return appContext.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    }

}