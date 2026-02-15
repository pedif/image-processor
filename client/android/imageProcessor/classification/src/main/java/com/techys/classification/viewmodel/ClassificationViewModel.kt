package com.techys.classification.viewmodel

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.techys.classification.model.ClassificationState
import com.techys.classification.model.ImageSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.techys.classification.R
import com.techys.classification.mapper.getMessage
import com.techys.common.util.Logger
import com.techys.core.model.UiState
import com.techys.core.util.ImagePreparer
import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ClassificationViewModel(
    private val classificationUseCase: ImageClassifyUseCase,
    private val imagePreparer: ImagePreparer,
    private val dispatcher: CoroutineDispatcher,
    private val logger: Logger
) : ViewModel() {
    companion object {
        private val tag = ClassificationViewModel::class.java.name
    }

    private val _state = MutableStateFlow(ClassificationState())
    val state: StateFlow<ClassificationState> = _state.asStateFlow()

    private val _errorMessages = MutableSharedFlow<Int>()
    val errorMessages: SharedFlow<Int> = _errorMessages

    fun setImageSource(uri: Uri) {
        logger.e(uri.toString(), "tagtag")
        updateState { copy(image = ImageSource(uri)) }
    }

    fun classify() = viewModelScope.launch(dispatcher) {
        val uri = state.value.image?.uri ?: run {
            _errorMessages.emit(R.string.error_nothing_selected)
            logger.e(tag = tag, text = "image null")
            return@launch
        }
        val file = try {
            withContext(Dispatchers.IO) { imagePreparer.prepare(uri) }
        } catch (e: Exception) {
            logger.e(tag = tag, text = "prepare failed: ${e.message}")
            _errorMessages.emit(R.string.error_classification_file_error)
            return@launch
        }
        classify(file)
    }

    @VisibleForTesting
    fun classify(file: File) = viewModelScope.launch(dispatcher) {
        logger.e(tag = tag, text = "classifying")
        updateState { copy(uiState = UiState.Loading) }
        val result = withContext(Dispatchers.IO) {
            classificationUseCase(file = file)
        }
        logger.e(
            tag = tag,
            text = "$result"
        )
        when (result) {
            is ClassificationResult.Success -> {
                updateState {
                    copy(
                        uiState = UiState.Idle,
                        label = result.label
                    )
                }
            }

            is ClassificationResult.NotRecognized -> {
                updateState {
                    copy(
                        uiState = UiState.Idle
                    )
                }
                _errorMessages.emit(R.string.classification_unsuccessful)
            }

            is ClassificationResult.Error -> {
                _errorMessages.emit(result.error.getMessage())
            }

            else -> {}
        }
    }

    private fun updateState(transform: ClassificationState.() -> ClassificationState) {
        _state.update { it.transform() }
    }
}
