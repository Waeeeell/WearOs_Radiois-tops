package com.example.radioistops.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.delay

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(
    mensajeApi: String = "Sal a dar un paseo de 15\nminutos por el parque.",
    diasSuperados: Int = 7,
    diasRestantes: Int = 7
) {
    // Estado para la hora y fecha real
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "ES")) }

    // Actualizar la hora cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }

    // Calculamos la proporción para la barra curva
    val totalDias = diasSuperados + diasRestantes
    val ratio = if (totalDias > 0) diasSuperados.toFloat() / totalDias else 0.5f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // --- 1. CONTENIDO CENTRAL ---
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Batería
            Row(
                modifier = Modifier
                    .background(Color(0xFF4CAF50), RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "72%",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Fecha Actual (Ej: 13 de diciembre)
            Text(
                text = LocalDate.now().format(dateFormatter),
                color = Color.White,
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium
            )

            //Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(8.dp))

            // Hora Real Central (Ej: 18:31)
            Text(
                text = currentTime.format(timeFormatter),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.offset(y = (-4).dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Mensaje Dinámico
            Text(
                text = mensajeApi,
                color = Color.White,
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        // --- 2. BARRA CURVA DE PROGRESO ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeW = 10.dp.toPx()
            val inset = 10.dp.toPx()
            val canvasSize = size.width - (inset * 2)

            val startAngle = 20f
            val sweepTotal = 140f


            // Fondo de la barra
            drawArc(
                color = Color(0xFF2088A5),
                startAngle = startAngle,
                sweepAngle = sweepTotal,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(canvasSize, canvasSize),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )

            // Progreso
            val sweepGreen = sweepTotal * (1 - ratio)
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = startAngle,
                sweepAngle = sweepGreen,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(canvasSize, canvasSize),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )
        }

        // --- 3. TEXTOS EN LOS EXTREMOS DE LA BARRA ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotate(160f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$diasSuperados dias",
                color = Color.White,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .rotate(-90f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotate(20f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$diasRestantes dias",
                color = Color.White,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .rotate(-90f)
            )
        }
    }
}
