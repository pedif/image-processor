package com.techys.imagecapture.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techys.common.util.Logger
import com.techys.imagecapture.R
import com.techys.imagecapture.model.CaptureState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageCaptureViewmodel @Inject constructor(
    val logger: Logger
) : ViewModel() {

    private var _state = MutableStateFlow<CaptureState>(CaptureState.Idle)
    val state: StateFlow<CaptureState> = _state

    private val _errorMessages = MutableSharedFlow<Int>()
    val errorMessages: SharedFlow<Int> = _errorMessages

    fun saveImageCapture(uri: Uri?) {
        _state.update {
            logger.e("capture", "$uri")
            if (uri == null)
                CaptureState.Error(com.techys.imagecapture.R.string.action_retry)
            else
                CaptureState.ImageCaptured(path = uri.toString())
        }
    }

    fun retry() {
        _state.update { CaptureState.Idle }
    }

    fun onCaptureError(message: String) = viewModelScope.launch {
        _errorMessages.emit(R.string.error_capture)
    }

    fun onCameraBindError(message: String) = viewModelScope.launch {
        _errorMessages.emit(R.string.error_camera)
    }
}