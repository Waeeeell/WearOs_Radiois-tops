package com.example.radioistops.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radioistops.data.model.WatchEstado
import com.example.radioistops.data.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var estado by mutableStateOf<WatchEstado?>(null)
        private set
    var isLoading by mutableStateOf(true)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    // El CIP del paciente logueado — por ahora hardcodeado,
    // luego vendrá del login/SharedPreferences
    private val cipPaciente = "LOMA020811005"

    init {
        cargarEstado()
        // Refresco automático cada 5 minutos
        viewModelScope.launch {
            while (true) {
                delay(5 * 60 * 1000L)
                cargarEstado()
            }
        }
    }

    fun cargarEstado() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                estado = RetrofitClient.apiService.getEstadoReloj(cipPaciente)
            } catch (e: Exception) {
                error = "Sin conexión con el servidor"
            } finally {
                isLoading = false
            }
        }
    }
}
