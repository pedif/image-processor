package com.techys.imagecapture.screen

import android.Manifest
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.techys.imagecapture.model.CaptureState
import com.techys.imagecapture.viewmodel.ImageCaptureViewmodel
import com.techys.ip.designsystem.theme.ImageProcessorTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageCaptureScreen(
    viewmodel: ImageCaptureViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onImageSelectedCLick: (String?) -> Unit = {},
) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val state by viewmodel.state.collectAsState()
    if (permissionState.status.isGranted) {
        ImageCaptureScreen(
            state = state,
            modifier = modifier.fillMaxSize(),
            onImageCaptured = viewmodel::saveImageCapture,
            onRetryClick = viewmodel::retry,
            onCaptureError = viewmodel::onCaptureError,
            onCameraBindError = viewmodel::onCameraBindError,
            onAcceptClick = { onImageSelectedCLick((state as CaptureState.ImageCaptured).path) }
        )
    } else {
        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }
    }

}

@Composable
private fun ImageCaptureScreen(
    state: CaptureState,
    modifier: Modifier = Modifier,
    onImageCaptured: (Uri?) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onCaptureError: (String) -> Unit = {},
    onCameraBindError: (String) -> Unit = {},
    onAcceptClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    CameraSetup(
        previewView = previewView,
        imageCapture = imageCapture,
        lifecycleOwner = lifecycleOwner,
        context = context,
        onError = onCameraBindError
    )

    CameraPreviewComponent(
        state = state,
        previewView = previewView,
        imageCapture = imageCapture,
        onImageCaptured = onImageCaptured,
        modifier = modifier,
        onError = onCaptureError,
        onRetryClick = onRetryClick,
        onAcceptClick = onAcceptClick
    )
}

@Preview
@Composable
fun ImageCaptureScreenPreview() {
    ImageProcessorTheme {
        Surface {
            ImageCaptureScreen(state = CaptureState.Idle)
        }
    }
}