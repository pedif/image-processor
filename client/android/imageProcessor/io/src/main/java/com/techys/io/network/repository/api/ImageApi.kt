package com.techys.io.network.repository.api
import com.techys.io.network.model.ImageLabelDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageApi {

    @Multipart
    @POST("classify")
    suspend fun classifyImage(
        @Part image: MultipartBody.Part
    ): Response<ImageLabelDto>
}