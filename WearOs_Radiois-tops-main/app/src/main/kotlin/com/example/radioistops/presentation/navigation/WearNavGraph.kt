package com.example.radioistops.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.radioistops.presentation.screens.DetailScreen
import com.example.radioistops.presentation.screens.HomeScreen
import com.example.radioistops.presentation.screens.SOSScreen
import com.example.radioistops.presentation.screens.SensorsScreen
import com.example.radioistops.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun WearNavGraph() {
    val viewModel: MainViewModel = viewModel()
    
    // Configurar el Pager para manejar las 4 pantallas (páginas 0 a 3)
    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> HomeScreen(
                viewModel = viewModel,
                onNavigate = { _ ->
                    // Navegar programáticamente a Detail (Página 1) si se toca el botón en el Home
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
            1 -> DetailScreen()
            2 -> SOSScreen()
            3 -> SensorsScreen()
        }
    }
}
