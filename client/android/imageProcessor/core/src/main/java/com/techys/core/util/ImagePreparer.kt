package com.techys.core.util

import android.net.Uri
import java.io.File

/**
 * Prepares an image for upload (e.g. copy or resize/compress) and returns a temp file
 */
interface ImagePreparer {
    /**
     * Reads the image at [uri]. If it's under the server size limit, copies as-is
     * (preserving format). If over the limit, resizes/compresses and writes to a temp file.
     * @return temp file suitable for upload (caller should delete when done if needed)
     * @throws Exception if the URI cannot be read or image cannot be processed
     */
    suspend fun prepare(uri: Uri): File
}
