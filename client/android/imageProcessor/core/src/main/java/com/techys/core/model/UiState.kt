package com.techys.core.model

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    //not putting error state here since we don't want to preserve the error state
}
