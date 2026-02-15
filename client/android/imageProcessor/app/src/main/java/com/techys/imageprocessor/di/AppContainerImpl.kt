package com.techys.imageprocessor.di

import android.content.Context
import com.techys.common.util.Logger
import com.techys.core.di.AppContainer
import com.techys.core.util.AndroidLogger
import com.techys.core.util.ImagePreparer
import com.techys.classification.util.ImagePreparerImpl
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
    override val repository: ImageRepository = ImageRepositoryStub()
    override val classificationUseCase: ImageClassifyUseCase = ImageClassifyUseCase(repository)
    override val dispatcher: CoroutineDispatcher = Dispatchers.Main
}