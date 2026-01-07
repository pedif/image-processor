package com.techys.core.di

import com.techys.common.util.Logger
import com.techys.ip.domain.repository.ImageRepository
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import kotlinx.coroutines.CoroutineDispatcher

interface AppContainer {
    val Logger: Logger
    val repository: ImageRepository
    val classificationUseCase: ImageClassifyUseCase
    val dispatcher: CoroutineDispatcher
}