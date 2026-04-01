package com.example.radioistops.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radioistops.domain.models.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _homeState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val homeState: StateFlow<UiState<String>> = _homeState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _homeState.value = UiState.Loading
            delay(1500) // Simulación red/sensores futura
            _homeState.value = UiState.Success("Sistema Base Inicializado")
        }
    }
}
