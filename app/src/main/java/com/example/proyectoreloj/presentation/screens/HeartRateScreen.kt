package com.example.radioistops.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.math.sin

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun HeartRateScreenPreview() {
    HeartRateScreen()
}

@Composable
fun HeartRateScreen(
    bpm: Int = 72,
    onClick: () -> Unit = {}
) {
    // Estado para la animación del gráfico
    var phase by remember { mutableStateOf(0f) }
    
    // Simulación de movimiento constante
    LaunchedEffect(Unit) {
        while (true) {
            phase -= 0.05f // Velocidad del barrido
            delay(16) // ~60 FPS
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Número BPM grande y centrado
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(start = 15.dp) // Ajuste para centrar visualmente con el texto BPM
            ) {
                Text(
                    text = "$bpm",
                    color = Color.White,
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "BPM",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                )
            }

            // Gráfico EKG
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                EKGGraph(phase = phase)
            }
        }
    }
}

@Composable
fun EKGGraph(phase: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val centerY = height / 2
        val gridStep = 20.dp.toPx()

        // --- DIBUJAR CUADRÍCULA SUTIL ---
        for (x in 0 until (width / gridStep).toInt() + 1) {
            drawLine(
                color = Color.DarkGray.copy(alpha = 0.3f),
                start = Offset(x * gridStep, 0f),
                end = Offset(x * gridStep, height),
                strokeWidth = 1.dp.toPx()
            )
        }
        for (y in 0 until (height / gridStep).toInt() + 1) {
            drawLine(
                color = Color.DarkGray.copy(alpha = 0.3f),
                start = Offset(0f, y * gridStep),
                end = Offset(width, y * gridStep),
                strokeWidth = 1.dp.toPx()
            )
        }

        // --- DIBUJAR LÍNEA ROJA (ONDA) ---
        val path = Path()
        val segments = 100
        var lastX = 0f
        var lastY = centerY

        for (i in 0..segments) {
            val x = (width / segments) * i
            // Generamos una onda que simula un latido (complejo QRS simplificado)
            val relX = (i.toFloat() / segments) * 10f + phase
            val sinVal = sin(relX * 2f)
            
            // Lógica para crear picos "médicos"
            val peak = if (sinVal > 0.9f) {
                -centerY * 0.8f // Pico hacia arriba
            } else if (sinVal < -0.98f) {
                centerY * 0.2f // Pequeño valle
            } else {
                sin(relX * 10f) * 2f // Ruido base sutil
            }

            val y = centerY + peak

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            
            lastX = x
            lastY = y
        }

        drawPath(
            path = path,
            color = Color(0xFFD32F2F), // Rojo fuerte sólido
            style = Stroke(width = 3.dp.toPx())
        )

        // --- DIBUJAR LA BOLA (Punto azul al final) ---
        drawCircle(
            color = Color(0xFFADDCFF), // Azul claro
            radius = 4.dp.toPx(),
            center = Offset(lastX, lastY)
        )
    }
}