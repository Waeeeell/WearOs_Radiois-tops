package com.example.radioistops.presentation.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    HomeScreen(
        mensajeApi = "Aislamiento total.\nEvita todo contacto.",
        diasSuperados = 2,
        diasRestantes = 6
    )
}

@Composable
fun HomeScreen(
    mensajeApi: String = "Aislamiento total.\nEvita todo contacto.",
    diasSuperados: Int = 2,
    diasRestantes: Int = 6
) {
    var batteryLevel by remember { mutableStateOf(0) }
    val context = LocalContext.current

    val batteryReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
                val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
                if (level != -1 && scale != -1) {
                    batteryLevel = (level * 100 / scale.toFloat()).toInt()
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(batteryReceiver, filter)
        onDispose { context.unregisterReceiver(batteryReceiver) }
    }

    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "ES")) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = LocalTime.now()
            delay(1000)
        }
    }

    val totalDias = diasSuperados + diasRestantes
    val ratio = if (totalDias > 0) diasSuperados.toFloat() / totalDias else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Batería
            Row(
                modifier = Modifier
                    .background(Color(0xFF4CAF50), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$batteryLevel%",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = LocalDate.now().format(dateFormatter),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentTime.format(timeFormatter),
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.offset(y = (-4).dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = mensajeApi,
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        // ── BARRA CURVA CORREGIDA ──────────────────────────────────────────────
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeW    = 14.dp.toPx()
            val inset      = 14.dp.toPx()
            val canvasSize = size.width - (inset * 2)
            val startAngle = 20f          // Empieza en la parte inferior derecha
            val sweepTotal = 140f         // Va de derecha a izquierda

            // 1. AZUL (derecha) — Días RESTANTES (Se dibuja primero)
            val sweepRestantes = sweepTotal * (1f - ratio)
            drawArc(
                color = Color(0xFF2088A5),
                startAngle = startAngle,
                sweepAngle = sweepRestantes,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(canvasSize, canvasSize),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )

            // 2. VERDE (izquierda) — Días SUPERADOS (Se dibuja a continuación)
            val sweepSuperados = sweepTotal * ratio
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = startAngle + sweepRestantes, // Empieza donde terminó el azul
                sweepAngle = sweepSuperados,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(canvasSize, canvasSize),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )
        }

        // ── ETIQUETAS EXTREMOS ─────────────────────────────────────────────────
        // Izquierda → Días superados (Verde)
        Box(
            modifier = Modifier.fillMaxSize().rotate(152f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$diasSuperados ${if (diasSuperados == 1) "dia" else "dias"}",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 14.dp).rotate(-90f)
            )
        }

        // Derecha → Días restantes (Azul)
        Box(
            modifier = Modifier.fillMaxSize().rotate(28f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$diasRestantes ${if (diasRestantes == 1) "dia" else "dias"}",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 14.dp).rotate(-90f)
            )
        }
    }
}