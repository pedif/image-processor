package com.techys.classification.viewmodel

import androidx.lifecycle.ViewModel
import com.techys.classification.model.ClassificationState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class ClassificationViewModel: ViewModel() {

    private val _state = MutableStateFlow<ClassificationState>(ClassificationState())
    val state: StateFlow<ClassificationState> = _state

    private val _errorMessages = MutableSharedFlow<String>()
    val errorMessages: SharedFlow<String> = _errorMessages




}