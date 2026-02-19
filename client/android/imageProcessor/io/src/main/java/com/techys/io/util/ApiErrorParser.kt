package com.techys.io.util

import com.squareup.moshi.Moshi
import com.techys.common.util.Logger
import okhttp3.ResponseBody

class ApiErrorParser(val moshi: Moshi, val logger: Logger) {

    inline fun <reified T> parseError(errorBody: ResponseBody?): T? {
        if (errorBody == null) {
            logger.d("error body is null", ApiErrorParser::class.java.name)
            return null
        }
        return try {
            val json = errorBody.string()
            val adapter = moshi.adapter(T::class.java)
            val errorResponse = adapter.fromJson(json)
            errorResponse as T
        } catch (e: Exception) {
            logger.e(e.message.orEmpty(), ApiErrorParser::class.java.name)
            null
        }
    }
}