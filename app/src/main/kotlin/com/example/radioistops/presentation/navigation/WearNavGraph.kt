package com.example.radioistops.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

import com.example.radioistops.presentation.screens.HomeScreen
import com.example.radioistops.presentation.screens.ActivityScreen
import com.example.radioistops.presentation.screens.EcgIntroScreen
import com.example.radioistops.presentation.screens.EcgRecordingScreen
import com.example.radioistops.presentation.screens.O2intro
import com.example.radioistops.presentation.screens.O2recording
import com.example.radioistops.presentation.screens.SosScreen

@Composable
fun WearNavGraph() {
    val pagerState = rememberPagerState(pageCount = { 7 })
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> HomeScreen()
            1 -> ActivityScreen()
            2 -> EcgIntroScreen(onStartEcg = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(3)
                }
            })
            3 -> EcgRecordingScreen()
            4 -> O2intro()
            5 -> O2recording()
            6 -> SosScreen()
        }
    }
}