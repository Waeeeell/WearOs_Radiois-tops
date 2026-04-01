package com.example.radioistops.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.example.radioistops.domain.models.UiState
import com.example.radioistops.presentation.viewmodels.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, onNavigate: (String) -> Unit) {
    val state by viewModel.homeState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val safeState = state) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Error status")
            is UiState.Success -> {
                Button(onClick = { onNavigate("detail") }) {
                    Text("Go to Detail")
                }
            }
        }
    }
}

@Composable
fun DetailScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Detail Base")
    }
}

@Composable
fun SOSScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("SOS Base")
    }
}

@Composable
fun SensorsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Sensors Base")
    }
}
