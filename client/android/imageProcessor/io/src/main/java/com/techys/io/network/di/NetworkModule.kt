package com.techys.io.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.techys.common.util.Logger
import com.techys.io.network.repository.NetworkImageRepository
import com.techys.io.network.repository.api.ImageApi
import com.techys.io.util.ApiErrorParser
import com.techys.ip.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://image-processor--pedif.replit.app/"
    private const val CONNECTION_TIMEOUT_INTERVAL = 10L
    private const val READ_TIMEOUT_INTERVAL = 20L
    private const val WRITE_TIMEOUT_INTERVAL = 20L

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT_INTERVAL, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_INTERVAL, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_INTERVAL, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideImageApi(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ImageApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiErrorParser(
        moshi: Moshi,
        logger: Logger
    ): ApiErrorParser {
        return ApiErrorParser(
            moshi = moshi,
            logger = logger
        )
    }

//    @Provides
//    @Singleton
//    fun provideImageRepository(
//        impl: NetworkImageRepository
//    ): ImageRepository {
//        return impl
//    }
}
