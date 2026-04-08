package com.example.radioistops.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text

@Composable
fun EcgRecordingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("EcgRecordingScreen", style = androidx.wear.compose.material.MaterialTheme.typography.caption2)
    }
}
