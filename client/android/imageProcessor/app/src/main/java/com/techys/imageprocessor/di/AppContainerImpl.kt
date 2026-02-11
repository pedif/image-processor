package com.techys.imageprocessor.di

import com.techys.common.util.Logger
import com.techys.core.di.AppContainer
import com.techys.ip.domain.repository.ImageRepository
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import com.techys.core.util.AndroidLogger
import com.techys.ip.domain.repository.ImageRepositoryStub
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Manual dependency injector
 * TODO replace with Hilt
 */
object AppContainerImpl: AppContainer {
    override val logger: Logger
        get() = AndroidLogger()
    override val repository: ImageRepository
        get() = ImageRepositoryStub()
    override val classificationUseCase: ImageClassifyUseCase
        get() = ImageClassifyUseCase(repository)
    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}