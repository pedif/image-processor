package com.techys.io.network.repository

import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.model.ImageLabel
import com.techys.ip.domain.repository.ImageRepository
import java.io.File

/**
 * A stub repository to run the Android app without a real backend
 */
class ImageRepositoryStub : ImageRepository {

    override suspend fun classifyImage(file: File): ClassificationResult {
        return ClassificationResult.Success(
            ImageLabel(label = "This is a cat", confidence = .85f)
        )
    }
}
