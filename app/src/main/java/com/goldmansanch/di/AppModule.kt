package com.goldmansanch.di

import com.goldmansanch.util.DateUtil
import com.goldmansanch.util.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Abhishek on 26,February,2022 For GolmanSanch
 */

@InstallIn(SingletonComponent::class)
@Module
class AppModule
{
    /**
     * Provide native Keys
     */
    @Provides
    fun provideKeys(): Keys = Keys

    @Provides
    fun provideDateUtils(): DateUtil = DateUtil

}