package com.example.radioistops.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.radioistops.R

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun EcgRecordingScreenPreview() {
    EcgRecordingScreen()
}

@Composable
fun EcgRecordingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Cambiado a negro puro
            .padding(horizontal = 12.dp), // Cambiado a 12.dp de margen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 1. Texto de instrucción
        Text(
            text = "Mantenga pulsado el botón superior\npara realizar el electrocardiograma",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp, // Ajustado a 7.sp como en la intro
            textAlign = TextAlign.Center,
            lineHeight = 14.sp // Interlineado ajustado a 14.sp
        )

        Spacer(modifier = Modifier.height(7.dp)) // Espaciador ajustado a 7.dp

        // 2. La imagen del ECG
        Box(
            modifier = Modifier
                .height(55.dp) // Altura de la caja ajustada a 55.dp
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ecg),
                contentDescription = "Línea ECG en progreso",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(6.dp)) // Espaciador ajustado a 6.dp

        // 3. Temporizador
        // Nota: Mantenemos el 22.sp solo aquí porque es un contador numérico gigante por diseño,
        // pero su posición ahora respeta la estructura estricta de la pantalla anterior.
        Text(
            text = "23s",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}