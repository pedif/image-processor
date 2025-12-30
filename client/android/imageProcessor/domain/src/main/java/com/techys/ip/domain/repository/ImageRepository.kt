package com.techys.ip.domain.repository

import com.techys.ip.domain.model.ClassificationResult
import java.io.File

interface ImageRepository{
    suspend fun classifyImage(file: File): ClassificationResult
}
