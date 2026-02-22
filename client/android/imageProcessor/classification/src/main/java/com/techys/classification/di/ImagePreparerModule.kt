package com.techys.classification.di

import com.techys.classification.util.ImagePreparer
import com.techys.classification.util.ImagePreparerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImagePreparerModule {
    @Binds
    @Singleton
    abstract fun bindImagePreparer(impl: ImagePreparerImpl): ImagePreparer

}