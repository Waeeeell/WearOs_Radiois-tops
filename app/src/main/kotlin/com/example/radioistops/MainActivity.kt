package com.example.radioistops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.radioistops.presentation.screens.*

import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            // Ahora tenemos 6 páginas fijas en el scroll. La de grabación es "especial".
            val pagerState = rememberPagerState(pageCount = { 6 })
            var showRecording by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            MaterialTheme {
                Scaffold(
                    timeText = { 
                        // Ocultamos hora en Intro (página 2) y en grabación
                        if (pagerState.currentPage != 2 && !showRecording) {
                            TimeText() 
                        }
                    }
                ) {
                    if (showRecording) {
                        // Si se activó la grabación, la mostramos sola
                        EcgRecordingScreen()
                        // Aquí podrías añadir lógica para volver (ej: un botón atrás o timer)
                    } else {
                        WearNavGraph(pagerState) {
                            showRecording = true
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WearNavGraph(
    pagerState: androidx.compose.foundation.pager.PagerState,
    onStartEcg: () -> Unit
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> HomeScreen()
            1 -> ActivityScreen()
            2 -> EcgIntroScreen(onStartEcg = onStartEcg)
            3 -> SosScreen()
            4 -> O2intro()
            5 -> O2recording()
        }
    }
}