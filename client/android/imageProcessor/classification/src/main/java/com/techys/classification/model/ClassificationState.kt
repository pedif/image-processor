package com.techys.classification.model

import com.techys.core.model.UiState
import com.techys.ip.domain.model.ImageLabel

data class ClassificationState(
    val state: UiState = UiState.Idle,
    val label: ImageLabel? = null,
    val image: ImageSource? = null
)
