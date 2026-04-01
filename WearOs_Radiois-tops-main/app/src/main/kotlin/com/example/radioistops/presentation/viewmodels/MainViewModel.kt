package com.example.radioistops.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radioistops.domain.models.HomeData
import com.example.radioistops.domain.models.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _homeState = MutableStateFlow<UiState<HomeData>>(UiState.Loading)
    val homeState: StateFlow<UiState<HomeData>> = _homeState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _homeState.value = UiState.Loading
            delay(1000) // Simular carga inicial rápida de API
            
            // Loop de simulador en tiempo real
            var beatAlternate = false
            while(true) {
                // Actualizamos hora dinámicamente
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                val currentDate = SimpleDateFormat("dd 'de' MMMM", Locale("es", "ES")).format(Date())

                val newData = HomeData(
                    batteryText = "72%",
                    batteryLevel = 0.72f,
                    dateString = currentDate,
                    timeString = currentTime,
                    dynamicMessage = "Sal a dar un paseo de 15 minutos por el parque.",
                    heartRateBpm = if (beatAlternate) 78 else 79,
                    spO2Percentage = 95,
                    leftProgressionText = "7 días",
                    rightProgressionText = "7 días"
                )
                
                _homeState.value = UiState.Success(newData)
                beatAlternate = !beatAlternate
                
                delay(2000) // Refresco cada 2 segundos
            }
        }
    }
}
