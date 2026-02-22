package com.techys.imagecapture.model

sealed class CaptureState {
    data object Idle : CaptureState()
    data class ImageCaptured(val path: String) : CaptureState()
    data class Error(val messageRes: Int) : CaptureState()
}