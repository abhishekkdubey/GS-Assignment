package com.goldmansanch.di

import com.goldmansanch.repository.ApodRepository
import com.goldmansanch.repository.ApodRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Abhishek on 27,February,2022 For GolmanSanch
 *
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule
{
    @Binds
    abstract fun provideAPODRepository(impl: ApodRepositoryImpl): ApodRepository
}