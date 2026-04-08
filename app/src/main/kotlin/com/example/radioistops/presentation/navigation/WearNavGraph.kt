package com.example.radioistops.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Importamos única y exclusivamente TUS pantallas
import com.example.radioistops.presentation.screens.HomeScreen
import com.example.radioistops.presentation.screens.ActivityScreen
import com.example.radioistops.presentation.screens.EcgIntroScreen
import com.example.radioistops.presentation.screens.EcgRecordingScreen
import com.example.radioistops.presentation.screens.O2intro
import com.example.radioistops.presentation.screens.O2recording
import com.example.radioistops.presentation.screens.SosScreen

@Composable
fun WearNavGraph() {
    // Definimos exactamente tus 7 pantallas
    val pagerState = rememberPagerState(pageCount = { 7 })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> HomeScreen()
            1 -> ActivityScreen()
            2 -> EcgIntroScreen()
            3 -> EcgRecordingScreen()
            4 -> O2intro()
            5 -> O2recording()
            6 -> SosScreen()
        }
    }
}