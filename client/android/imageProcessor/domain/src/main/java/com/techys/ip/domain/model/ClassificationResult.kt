package com.techys.ip.domain.model

/**
 * Result wrapper for classification operations done by
 * either backend or offline
 */
sealed class ClassificationResult {
    data class Success(val label: ImageLabel) : ClassificationResult()
    object NotRecognized : ClassificationResult()
    data class Error(val reason: String) : ClassificationResult()
}
