package com.techys.io.network.repository

import com.techys.common.util.Logger
import com.techys.io.mapper.toDomain
import com.techys.io.mapper.toMultipart
import com.techys.io.network.model.ErrorResponse
import com.techys.io.network.repository.api.ImageApi
import com.techys.io.util.ApiErrorParser
import com.techys.ip.domain.model.ClassificationError
import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.repository.ImageRepository
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkImageRepository(
    val api: ImageApi,
    val logger: Logger,
    val errorParser: ApiErrorParser
) : ImageRepository {

    override suspend fun classifyImage(file: File): ClassificationResult {
        try {
            val response = api.classifyImage(file.toMultipart())
            return if (response.isSuccessful) {
                ClassificationResult.Success(response.body()!!.toDomain())
            } else {
                val errorResponse = errorParser.parseError<ErrorResponse>(response.errorBody())
                errorResponse?.let {
                    return ClassificationResult.Error(errorResponse.toDomain())
                }
                logger.d("no error response from server", "classifyImage")
                return ClassificationResult.Error(
                    ClassificationError.Unknown(
                        response.message() ?: ""
                    )
                )
            }
        } catch (e: SocketTimeoutException) {
            logger.e(e.message.orEmpty(), "classifyImage")
            return ClassificationResult.Error(ClassificationError.Timeout)
        } catch (e: IOException) {
            logger.e(e.message.orEmpty(), "classifyImage")
            return ClassificationResult.Error(ClassificationError.Network)
        }
    }
}
