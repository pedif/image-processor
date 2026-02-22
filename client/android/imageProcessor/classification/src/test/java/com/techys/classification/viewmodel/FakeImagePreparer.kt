package com.techys.classification.viewmodel

import android.net.Uri
import com.techys.classification.util.ImagePreparer
import java.io.File
import java.io.IOException
import kotlin.io.path.createTempFile

class FakeImagePreparer(
    private val shouldFail: Boolean = false
) : ImagePreparer {

    override suspend fun prepare(uri: Uri): File {
        if (shouldFail) throw IOException("no file")
        return createTempFile().toFile()
    }
}