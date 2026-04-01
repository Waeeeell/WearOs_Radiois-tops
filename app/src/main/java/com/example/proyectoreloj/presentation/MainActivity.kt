package com.example.proyectoreloj.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.example.proyectoreloj.presentation.screens.*
import com.example.proyectoreloj.presentation.theme.ProyectoRelojTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoRelojTheme {
                WearApp()
            }
        }
    }
}

@Composable
fun WearApp() {
    // Definimos el estado del Pager para 5 pantallas
    val pagerState = rememberPagerState(pageCount = { 5 })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            // Invocamos cada pantalla según el índice
            when (page) {
                0 -> HomeScreen()
                1 -> ActivityScreen()
                2 -> SosScreen()
                3 -> EcgIntroScreen()
                4 -> EcgRecordingScreen()
            }
        }
    }
}
