package com.techys.ip.domain.usecase

import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.repository.ImageRepository
import java.io.File

class ImageClassifyUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(file: File): ClassificationResult {
        return repository.classifyImage(file)
    }
}
