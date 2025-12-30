package com.techys.ip.domain.usecase

import com.techys.ip.domain.model.ClassificationError
import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.repository.ImageRepository
import java.io.File

class ImageClassifyUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(file: File): ClassificationResult {
        if (!file.exists()) {
            return ClassificationResult.Error(ClassificationError.FileNotFound)
        }
        return repository.classifyImage(file)
    }
}
