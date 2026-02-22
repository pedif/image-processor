package com.techys.io.mapper

import com.techys.io.network.model.ErrorResponse
import com.techys.io.network.model.ImageLabelDto
import com.techys.io.network.model.ServerErrorCodes
import com.techys.ip.domain.model.ClassificationError
import com.techys.ip.domain.model.ImageLabel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun ImageLabelDto.toDomain(): ImageLabel {
    return ImageLabel(label = label, confidence = confidence)
}

fun File.toMultipart(): MultipartBody.Part {
    val requestFile = this.asRequestBody("image/*".toMediaType())
    return MultipartBody.Part.createFormData(
        name = "file",   // must match backend field name
        filename = this.name,
        body = requestFile
    )
}

fun ErrorResponse.toDomain(): ClassificationError {
    return when (errorCode) {
        ServerErrorCodes.INVALID_IMAGE_FILE -> ClassificationError.InvalidImageFormat
        ServerErrorCodes.FILE_TOO_LARGE -> ClassificationError.FileTooLarge
        ServerErrorCodes.PROCESSING_CRASH -> ClassificationError.ProcessingCrash
        else -> ClassificationError.UnknownServerError(errorCode, message)
    }
}

