package com.techys.classification.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.techys.classification.util.ImagePreparer
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import androidx.core.graphics.scale
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val MAX_SIZE_BYTES = 5 * 1024 // 5MB server limit
private const val MAX_LONG_SIDE = 1024
private const val JPEG_QUALITY = 85

/**
 * Prepares a image for upload. If size is over 5MB, resizes and compresses to JPEG
 */
class ImagePreparerImpl @Inject constructor(
    @ApplicationContext val context: Context
) : ImagePreparer {

    override suspend fun prepare(uri: Uri): File {
        val size = context.contentResolver.openFileDescriptor(uri, "r")?.use { fd ->
            fd.statSize  // -1 if unknown
        } ?: throw IllegalArgumentException("Could not open URI: $uri")

        return if (size in 1..MAX_SIZE_BYTES) {
            copyToTempPreservingFormat(uri)
        } else {
            resizeAndCompressToJpeg(uri)
        }
    }

    /** Copy content to temp file; extension from content type (e.g. .jpg, .png). */
    private fun copyToTempPreservingFormat(uri: Uri): File {
        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
        val ext = when {
            mimeType.equals("image/png", ignoreCase = true) -> ".png"
            else -> ".jpg"
        }
        val file = File.createTempFile("upload_", ext, context.cacheDir)
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: throw IllegalArgumentException("Could not open URI: $uri")
        return file
    }

    /** Decode, resize, and write as JPEG to stay under size limit. */
    private fun resizeAndCompressToJpeg(uri: Uri): File {
        context.contentResolver.openInputStream(uri)?.use { input ->
            val (width, height) = decodeBounds(input)
            val sampleSize = computeSampleSize(width, height, MAX_LONG_SIDE)
            input.close()
            context.contentResolver.openInputStream(uri)?.use { input2 ->
                val options = BitmapFactory.Options().apply {
                    inSampleSize = sampleSize
                }
                val bitmap = BitmapFactory.decodeStream(input2, null, options)
                    ?: throw IllegalArgumentException("Could not decode image")
                val scaled = scaleIfNeeded(bitmap, MAX_LONG_SIDE)
                if (scaled != bitmap) bitmap.recycle()
                return writeToTempFileJpeg(scaled).also { scaled.recycle() }
            }
        }
        throw IllegalArgumentException("Could not open URI: $uri")
    }

    private fun decodeBounds(input: InputStream): Pair<Int, Int> {
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeStream(input, null, options)
        return Pair(options.outWidth, options.outHeight)
    }

    private fun computeSampleSize(width: Int, height: Int, maxLongSide: Int): Int {
        if (width <= 0 || height <= 0) return 1
        val longSide = maxOf(width, height)
        if (longSide <= maxLongSide) return 1
        var sampleSize = 1
        var half = longSide / 2
        while (half / sampleSize >= maxLongSide) sampleSize *= 2
        return sampleSize
    }

    private fun scaleIfNeeded(bitmap: Bitmap, maxLongSide: Int): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val longSide = maxOf(w, h)
        if (longSide <= maxLongSide) return bitmap
        val scale = maxLongSide.toFloat() / longSide
        val newW = (w * scale).toInt().coerceAtLeast(1)
        val newH = (h * scale).toInt().coerceAtLeast(1)
        return bitmap.scale(newW, newH)
    }

    private fun writeToTempFileJpeg(bitmap: Bitmap): File {
        val file = File.createTempFile("upload_", ".jpg", context.cacheDir)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, out)
        }
        return file
    }
}
