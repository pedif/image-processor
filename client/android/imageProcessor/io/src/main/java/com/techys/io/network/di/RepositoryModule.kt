package com.techys.io.network.di

import androidx.room.Insert
import com.techys.io.network.repository.NetworkImageRepository
import com.techys.ip.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindImageRepository(
        impl: NetworkImageRepository
    ): ImageRepository
}