package com.example.radioistops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.radioistops.data.model.WatchEstado
import com.example.radioistops.presentation.screens.*

import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import com.example.radioistops.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val pagerState = rememberPagerState(pageCount = { 5 })
            var showRecording by remember { mutableStateOf(false) }
            var showO2Recording by remember { mutableStateOf(false) }
            var ecgResult by remember { mutableStateOf<Int?>(null) }
            var o2Result by remember { mutableStateOf<Int?>(null) }

            val vm: MainViewModel = viewModel()
            val estado = vm.estado

            MaterialTheme {
                Scaffold(
                    timeText = {
                        if (!showRecording && !showO2Recording && ecgResult == null && o2Result == null) {
                            TimeText()
                        }
                    }
                ) {
                    when {
                        showRecording -> EcgRecordingScreen(onFinish = { result -> showRecording = false; ecgResult = result })
                        showO2Recording -> O2recording(onFinish = { result -> showO2Recording = false; o2Result = result })
                        ecgResult != null -> EcgResultScreen(result = ecgResult!!, onFinish = { ecgResult = null })
                        o2Result != null -> O2ResultScreen(result = o2Result!!, onFinish = { o2Result = null })
                        else -> WearNavGraph(pagerState, { showRecording = true }, { showO2Recording = true }, estado)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WearNavGraph(
    pagerState: PagerState,
    onStartEcg: () -> Unit,
    onStartO2: () -> Unit,
    watchEstado: WatchEstado?
) {
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        when (page) {
            0 -> {
                if (watchEstado != null) {
                    HomeScreen(
                        mensajeApi = watchEstado.mensajeApi,
                        diasSuperados = watchEstado.diasSuperados,
                        diasRestantes = watchEstado.diasRestantes
                        // Nota: HomeScreen no tiene parámetro porcentajeBateria aún
                    )
                } else {
                    HomeScreen()
                }
            }
            1 -> {
                if (watchEstado != null) {
                    ActivityScreen(
                        diasSuperados = watchEstado.diasSuperados,
                        diasRestantes = watchEstado.diasRestantes,
                        diaActual = watchEstado.diaActual
                        // Nota: ActivityScreen no tiene parámetros de título/mensaje aún
                    )
                } else {
                    ActivityScreen()
                }
            }
            2 -> EcgIntroScreen(onStartEcg)
            3 -> SosScreen()
            4 -> O2intro(onStartMeasure = onStartO2)
        }
    }
}