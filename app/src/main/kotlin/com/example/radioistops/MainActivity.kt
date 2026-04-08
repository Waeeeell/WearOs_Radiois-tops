package com.example.radioistops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
// Import your screens from your actual project package
import com.example.radioistops.presentation.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WearNavGraph()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WearNavGraph() {
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
            4 -> SosScreen()
            5 -> O2intro()
            6 -> O2recording()
        }
    }
}