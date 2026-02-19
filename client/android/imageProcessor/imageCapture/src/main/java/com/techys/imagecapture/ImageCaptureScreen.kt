package com.techys.imagecapture

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.techys.ip.designsystem.theme.Dimen
import com.techys.ip.designsystem.theme.ImageProcessorTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageCaptureScreen(){
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (permissionState.status.isGranted) {
        ImageCaptureScreen(isImageCaptured = false,
            modifier = Modifier.fillMaxSize())
    } else {
        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }
    }

}

@Composable
private fun ImageCaptureScreen(
    isImageCaptured: Boolean,
    modifier: Modifier = Modifier){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }
    LaunchedEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val cameraProvider = cameraProviderFuture.get()
        val imageCapture = ImageCapture.Builder().build()
        val preview = androidx.camera.core.Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try{
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
        ActionAreaComponent(
            modifier = Modifier.fillMaxWidth().padding(bottom = Dimen.paddingScreenVertical).align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
fun ImageCaptureScreenPreview(){
    ImageProcessorTheme {
        Surface {
            ImageCaptureScreen()
        }
    }
}