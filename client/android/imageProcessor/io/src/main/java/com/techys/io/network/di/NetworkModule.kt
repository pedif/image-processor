package com.techys.io.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.techys.common.util.Logger
import com.techys.io.network.repository.NetworkImageRepository
import com.techys.io.network.repository.api.ImageApi
import com.techys.io.util.ApiErrorParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

class NetworkModule(
    val logger: Logger
) {

    companion object {
        private const val BASE_URL = "https://image-processor--pedif.replit.app/"
        private const val CONNECTION_TIMEOUT_INTERVAL = 10L
        private const val READ_TIMEOUT_INTERVAL = 20L

        private const val WRITE_TIMEOUT_INTERVAL = 20L
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(CONNECTION_TIMEOUT_INTERVAL, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_INTERVAL, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_INTERVAL, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val api: ImageApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ImageApi::class.java)
    }

    private val errorParser: ApiErrorParser = ApiErrorParser(
        moshi = moshi,
        logger = logger
    )

    fun provideNetworkRepository(): NetworkImageRepository {
        return NetworkImageRepository(
            api = api,
            logger = logger,
            errorParser = errorParser
        )
    }
}
