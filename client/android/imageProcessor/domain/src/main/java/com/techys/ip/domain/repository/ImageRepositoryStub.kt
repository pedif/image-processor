package com.techys.ip.domain.repository

import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.model.ImageLabel
import java.io.File

/**
 * A stub repository to run the Android app without a real backend
 */
class ImageRepositoryStub : ImageRepository {

    var result = ClassificationResult.Success(
        ImageLabel(label = "This is a cat", confidence = .85f)
    )

    override suspend fun classifyImage(file: File) = result
}