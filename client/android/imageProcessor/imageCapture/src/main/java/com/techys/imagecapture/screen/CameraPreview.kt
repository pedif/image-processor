package com.techys.imagecapture.screen

import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import coil3.compose.AsyncImage
import com.techys.imagecapture.model.CaptureState
import kotlinx.coroutines.guava.await
import java.io.File


@Composable
internal fun CameraSetup(
    previewView: PreviewView,
    imageCapture: ImageCapture,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    onError: (String) -> Unit = {}
) {
    LaunchedEffect(lifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val cameraProvider = cameraProviderFuture.await()
        val preview = androidx.camera.core.Preview.Builder()
            .build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.message.orEmpty())
        }
    }
}

@Composable
internal fun CameraPreviewComponent(
    state: CaptureState,
    previewView: PreviewView,
    imageCapture: ImageCapture,
    modifier: Modifier = Modifier,
    onImageCaptured: (Uri?) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onAcceptClick: () -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {
        if (state is CaptureState.ImageCaptured) {
            AsyncImage(
                model = state.path, contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
        }
        ActionAreaComponent(
            isImageCaptured = state is CaptureState.ImageCaptured,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onCaptureClick = {
                takePhoto(
                    context = context,
                    imageCapture = imageCapture,
                    onImageSaved = onImageCaptured,
                    onError = onError
                )
            },
            onRetryClick = onRetryClick,
            onAcceptClick = onAcceptClick
        )
    }
}

private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onImageSaved: (Uri?) -> Unit = {},
    onError: (String) -> Unit = {}
) {
    val photoFile = File(
        context.cacheDir,
        "captured_${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                onImageSaved(output.savedUri)
            }

            override fun onError(e: ImageCaptureException) {
                e.printStackTrace()
                onError(e.message.orEmpty())
            }
        }
    )
}
