package com.techys.imageprocessor.di

import android.content.Context
import com.techys.common.util.Logger
import com.techys.core.di.AppContainer
import com.techys.core.util.AndroidLogger
import com.techys.core.util.ImagePreparer
import com.techys.classification.util.ImagePreparerImpl
import com.techys.io.network.di.NetworkModule
import com.techys.io.network.repository.NetworkImageRepository
import com.techys.ip.domain.repository.ImageRepository
import com.techys.ip.domain.repository.ImageRepositoryStub
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Manual dependency injector
 * TODO replace with Hilt
 */
class AppContainerImpl(private val context: Context) : AppContainer {

    override val logger: Logger = AndroidLogger()
    override val imagePreparer: ImagePreparer = ImagePreparerImpl(context.applicationContext)
    override val dispatcher: CoroutineDispatcher = Dispatchers.Main
    private val networkModule: NetworkModule = NetworkModule(logger = logger)
    override val repository: ImageRepository = networkModule.provideNetworkRepository()
    override val classificationUseCase: ImageClassifyUseCase = ImageClassifyUseCase(repository)
}