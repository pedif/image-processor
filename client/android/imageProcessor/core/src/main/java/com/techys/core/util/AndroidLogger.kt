package com.techys.core.util

import com.techys.common.util.Logger
import timber.log.Timber
import javax.inject.Inject

class AndroidLogger @Inject constructor(): Logger {
    override fun d(text: String, tag: String) {
        Timber.tag(tag).d(text)
    }

    override fun e(text: String, tag: String) {
        Timber.tag(tag).e(text)
    }
}
