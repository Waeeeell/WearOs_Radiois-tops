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
            // Ahora tenemos 5 páginas fijas en el scroll. Las de grabación son "especiales".
            val pagerState = rememberPagerState(pageCount = { 5 })
            var showRecording by remember { mutableStateOf(false) }
            var showO2Recording by remember { mutableStateOf(false) }
            var ecgResult by remember { mutableStateOf<Int?>(null) }
            var o2Result by remember { mutableStateOf<Int?>(null) }
            val scope = rememberCoroutineScope()

            MaterialTheme {
                Scaffold(
                    timeText = { 
                        // Ocultamos hora en Home (0), ECG Intro (2), SOS (3), O2 Intro (4) y en grabaciones
                        if (pagerState.currentPage != 0 &&
                            pagerState.currentPage != 2 &&
                            pagerState.currentPage != 3 && 
                            pagerState.currentPage != 4 && 
                            !showRecording && !showO2Recording && ecgResult == null && o2Result == null) {
                            TimeText() 
                        }
                    }
                ) {
                    when {
                        showRecording -> {
                            EcgRecordingScreen(onFinish = { result ->
                                showRecording = false
                                ecgResult = result
                            })
                        }
                        showO2Recording -> {
                            O2recording(onFinish = { result ->
                                showO2Recording = false
                                o2Result = result
                            })
                        }
                        ecgResult != null -> {
                            EcgResultScreen(result = ecgResult!!, onFinish = { ecgResult = null })
                        }
                        o2Result != null -> {
                            O2ResultScreen(result = o2Result!!, onFinish = { o2Result = null })
                        }
                        else -> {
                            WearNavGraph(
                                pagerState = pagerState,
                                onStartEcg = { showRecording = true },
                                onStartO2 = { showO2Recording = true }
                            )
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
    onStartEcg: () -> Unit,
    onStartO2: () -> Unit
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
            4 -> O2intro(onStartMeasure = onStartO2)
        }
    }
}