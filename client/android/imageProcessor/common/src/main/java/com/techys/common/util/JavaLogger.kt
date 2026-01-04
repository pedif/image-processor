package com.techys.common.util

class JavaLogger: Logger {
    override fun d(text: String, tag: String) {
        println(text)
    }
    override fun e(text: String, tag: String) {
        println(text)
    }
}