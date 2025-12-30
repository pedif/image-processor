package com.techys.ip.domain.repository

import com.techys.ip.domain.model.ClassificationError
import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.model.ImageLabel
import java.io.File

class ImageRepositoryFake : ImageRepository {

    var result: ClassificationResult =
        ClassificationResult.Error(ClassificationError.Unknown())

    override suspend fun classifyImage(file: File): ClassificationResult = result
}
