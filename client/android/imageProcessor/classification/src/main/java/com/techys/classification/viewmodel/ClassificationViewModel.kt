package com.techys.classification.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.techys.classification.model.ClassificationState
import com.techys.classification.model.ImageSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.techys.classification.R
import com.techys.classification.mapper.getMessage
import com.techys.common.util.Logger
import com.techys.core.model.UiState
import com.techys.ip.domain.model.ClassificationResult
import com.techys.ip.domain.usecase.ImageClassifyUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class ClassificationViewModel(
    private val classificationUseCase: ImageClassifyUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val logger: Logger
) :
    ViewModel() {
    companion object {
        private val tag = ClassificationViewModel::class.java.name
    }

    private val _state = MutableStateFlow(ClassificationState())
    val state: StateFlow<ClassificationState> = _state.asStateFlow()

    private val _errorMessages = MutableSharedFlow<Int>()
    val errorMessages: SharedFlow<Int> = _errorMessages

    //TODO Remove all asset related placeholders when gallery/camera are setup
    fun loadAssetPlaceholder() {
        setImageSource("cat.png")
    }

    fun setImageSource(path: String) {
        updateState { copy(image = ImageSource(path.toUri())) }
    }

    fun classifyDemoAsset(context: Context) {
        classify(
            assetToFile(
                context = context,
                assetPath = "cat.png"
            )
        )
    }

    fun classify(file: File) = viewModelScope.launch(dispatcher) {
        logger.e(
            tag = tag,
            text = "classifying"
        )
        val imagePath = state.value.image?.uri
        if (imagePath == null) {
            _errorMessages.emit(R.string.error_nothing_selected)
            logger.e(
                tag = tag,
                text = "image null"
            )
            return@launch
        }
        updateState {
            copy(
                state = UiState.Loading
            )
        }
        val result = classificationUseCase(
            file = file
        )
        logger.e(
            tag = tag,
            text = "$result"
        )
        when (result) {
            is ClassificationResult.Success -> {
                updateState {
                    copy(
                        state = UiState.Idle,
                        label = result.label
                    )
                }
            }

            is ClassificationResult.NotRecognized -> {
                updateState {
                    copy(
                        state = UiState.Idle
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

    /**
     * Since for the sake of development we're using an asset file instead of using cameraX or gallery
     * we need to copy the packaged asset to a temp file and use it that way
     * TODO remove when cameraX and gallery are implemented
     */
    fun assetToFile(context: Context, assetPath: String): File {
        val outFile = File(context.cacheDir, assetPath.substringAfterLast('/'))

        context.assets.open(assetPath).use { input ->
            outFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return outFile
    }

}