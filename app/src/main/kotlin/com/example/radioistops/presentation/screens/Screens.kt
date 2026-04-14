package com.example.radioistops.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.example.radioistops.presentation.viewmodels.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, onNavigate: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else if (viewModel.error != null) {
            Text(text = viewModel.error ?: "Error")
        } else {
            Button(onClick = { onNavigate("detail") }) {
                Text("Go to Detail")
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
