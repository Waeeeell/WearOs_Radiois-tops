package com.example.proyectoreloj.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.proyectoreloj.presentation.navigation.Screen
import com.example.proyectoreloj.presentation.screens.*
import com.example.proyectoreloj.presentation.theme.ProyectoRelojTheme
import com.example.proyectoreloj.presentation.viewmodel.MotivationalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoRelojTheme {
                // Usamos un Box con fondo negro/tema para asegurar que no haya transparencia
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    Scaffold(
                        timeText = { TimeText() }
                    ) {
                        WearApp()
                    }
                }
            }
        }
    }
}

@Composable
fun WearApp() {
    val navController = rememberSwipeDismissableNavController()
    val motivationalViewModel: MotivationalViewModel = viewModel()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screen.HOME
    ) {
        composable(Screen.HOME) {
            HomeScreen(
                motivationalViewModel = motivationalViewModel,
                onNavigateToActivity = { navController.navigate(Screen.ACTIVITY) },
                onNavigateToSos = { navController.navigate(Screen.SOS) },
                onNavigateToEcg = { navController.navigate(Screen.ECG_INTRO) }
            )
        }
        composable(Screen.ACTIVITY) {
            ActivityScreen(motivationalViewModel = motivationalViewModel)
        }
        composable(Screen.SOS) {
            SosScreen(onDismiss = { navController.popBackStack() })
        }
        composable(Screen.ECG_INTRO) {
            EcgIntroScreen(onStartEcg = { navController.navigate(Screen.ECG_RECORDING) })
        }
        composable(Screen.ECG_RECORDING) {
            EcgRecordingScreen(onFinished = {
                navController.popBackStack(Screen.HOME, inclusive = false)
            })
        }
    }
}
