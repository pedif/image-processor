package com.techys.ip.domain.repository

import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.model.ImageLabel
import java.io.File

class ImageRepositoryFake : ImageRepository {

    var result: ClassificationResult =
        ClassificationResult.Error("Result not set")

    override suspend fun classifyImage(file: File): ClassificationResult = result
}
