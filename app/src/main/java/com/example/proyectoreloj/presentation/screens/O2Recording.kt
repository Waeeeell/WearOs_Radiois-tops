package com.example.radioistops.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import kotlinx.coroutines.delay

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun O2recordingPreview() {
    O2recording(onFinish = {})
}

@Composable
fun O2recording(onFinish: (Int) -> Unit = {}) {
    var secondsLeft by remember { mutableIntStateOf(30) }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000L)
            secondsLeft--
        }
        // Generamos un resultado aleatorio entre 95 y 99%
        val randomResult = (95..99).random()
        onFinish(randomResult)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 1. Título superior
        Text(
            text = "Lectura iniciada",
            color = Color(0xFF65D4F9),
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Temporizador activo
        Text(
            text = "${secondsLeft}S",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Texto de instrucciones inferior
        Text(
            text = "Permanezca quieto hasta que la lectura\nfinalice",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}
