package com.techys.io.network.model

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "error_code")
    val errorCode: String,
    val message: String
)
object ServerErrorCodes {
    const val INVALID_IMAGE_FILE = "INVALID_IMAGE_FILE"
    const val FILE_TOO_LARGE = "FILE_TOO_LARGE"
    const val PROCESSING_CRASH = "PROCESSING_CRASH"
}