package com.techys.core.di

import com.techys.common.util.Logger
import com.techys.core.util.ImagePreparer
import com.techys.ip.domain.repository.ImageRepository
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import kotlinx.coroutines.CoroutineDispatcher

interface AppContainer {
    val logger: Logger
    val imagePreparer: ImagePreparer
    val repository: ImageRepository
    val classificationUseCase: ImageClassifyUseCase
    val dispatcher: CoroutineDispatcher
}