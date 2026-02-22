package com.techys.core.di

import com.techys.common.util.Logger
import com.techys.core.util.AndroidLogger
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//object LoggingModule {
//
//    @Provides
//    @Singleton
//    fun provideLogger(): Logger = AndroidLogger()
//}
abstract class LoggingModule {

    @Binds
    @Singleton
    abstract fun provideLogger(logger: AndroidLogger): Logger
}