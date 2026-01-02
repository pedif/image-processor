package com.techys.imageprocessor.di

import com.techys.common.util.Logger
import com.techys.core.di.AppContainer
import com.techys.ip.domain.repository.ImageRepository
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import com.techys.core.util.AndroidLogger
import com.techys.io.network.repository.ImageRepositoryStub

/**
 * Manual dependency injector
 * TODO replace with Hilt
 */
object AppContainerImpl: AppContainer {
    override val Logger: Logger
        get() = AndroidLogger()
    override val repository: ImageRepository
        get() = ImageRepositoryStub()
    override val classificationUseCase: ImageClassifyUseCase
        get() = ImageClassifyUseCase(repository)
}