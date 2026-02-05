package com.techys.classification.screen

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max

suspend fun decodeBitmapFittingBox(
    context: Context,
    uri: Uri,
    targetWidth: Int,
    targetHeight: Int
): Bitmap? = withContext(Dispatchers.IO) {
    val resolver = context.contentResolver
    val bounds = readImageBounds(resolver, uri) ?: return@withContext null

    val (w, h) = resolveTargetSize(bounds, targetWidth, targetHeight)

    if (w == 0 || h == 0) return@withContext null

    val scale = max(
        bounds.width.toFloat() / w,
        bounds.height.toFloat() / h
    )

    val sampleSize = max(1, scale.toInt())
    // Pass 2: actual decode
    val decode = BitmapFactory.Options().apply {
        this.inSampleSize = sampleSize
        inPreferredConfig = Bitmap.Config.RGB_565
    }

    resolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(it, null, decode)
    }
}

@Composable
fun DownsampledImage(
    uri: Uri,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val context = LocalContext.current

    val bitmap by produceState<Bitmap?>(null, uri, size) {
        value = decodeBitmapFittingBox(
            context,
            uri,
            size.width,
            size.height
        )
    }

    Box(
        modifier = modifier.onSizeChanged { size = it },
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Cyan)
                    .align(Alignment.Center),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}
data class ImageBounds(val width: Int, val height: Int)

fun readImageBounds(
    resolver: ContentResolver,
    uri: Uri
): ImageBounds? {
    val opts = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    resolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(it, null, opts)
    }
    return if (opts.outWidth > 0 && opts.outHeight > 0)
        ImageBounds(opts.outWidth, opts.outHeight)
    else null
}
fun resolveTargetSize(
    image: ImageBounds,
    targetWidth: Int,
    targetHeight: Int
): Pair<Int, Int> {
    val aspect = image.width.toFloat() / image.height

    return when {
        targetWidth > 0 && targetHeight > 0 ->
            targetWidth to targetHeight

        targetWidth > 0 -> {
            val h = (targetWidth / aspect).toInt()
            targetWidth to h
        }

        targetHeight > 0 -> {
            val w = (targetHeight * aspect).toInt()
            w to targetHeight
        }

        else -> 0 to 0
    }
}