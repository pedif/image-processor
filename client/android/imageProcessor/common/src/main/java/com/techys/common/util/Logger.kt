package com.techys.common.util

interface Logger {
    fun d(text: String, tag: String = "")
    fun e(text: String, tag: String = "")
}