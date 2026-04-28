package com.example.radioistops.presentation.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.animation.*
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
fun ActivityScreenPreview() {
    ActivityScreen(
        diasSuperados = 2,
        diasRestantes = 6,
        diaActual = 3,
        instrucciones = listOf(
            "Dormir sol",
            "Rentar roba separada",
            "Dos descàrregues de cisterna",
            "Distància 1m amb adults",
            "No contacte amb infants",
            "Beu molta aigua"
        )
    )
}

@Composable
fun ActivityScreen(
    diasSuperados: Int = 2,
    diasRestantes: Int = 6,
    diaActual: Int = 3,
    instrucciones: List<String> = listOf("Mantente en aislamiento total.")
) {
    val context = LocalContext.current
    var batteryLevel by remember { mutableStateOf(0) }

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

    var indiceActual by remember { mutableStateOf(0) }

    LaunchedEffect(instrucciones) {
        indiceActual = 0
        if (instrucciones.size > 1) {
            while (true) {
                delay(4000L)
                indiceActual = (indiceActual + 1) % instrucciones.size
            }
        }
    }

    val totalDias = diasSuperados + diasRestantes
    val ratio = if (totalDias > 0) diasSuperados.toFloat() / totalDias else 0f

    // Lógica para el color de la batería
    val batteryColor = when {
        batteryLevel <= 15 -> Color(0xFFE53935) // Rojo
        batteryLevel <= 30 -> Color(0xFFFFB300) // Amarillo
        else -> Color(0xFF4CAF50)               // Verde
    }

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

            // Batería con color dinámico
            Row(
                modifier = Modifier
                    .background(batteryColor, RoundedCornerShape(50))
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

            Spacer(modifier = Modifier.height(35.dp))

            AnimatedContent(
                targetState = indiceActual,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "instruccion_rotante"
            ) { idx ->
                Text(
                    text = instrucciones.getOrElse(idx) { "" },
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }

            if (instrucciones.size > 1) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    instrucciones.forEachIndexed { i, _ ->
                        Box(
                            modifier = Modifier
                                .size(if (i == indiceActual) 5.dp else 3.dp)
                                .background(
                                    color = if (i == indiceActual) Color.White
                                    else Color(0xFF555555),
                                    shape = RoundedCornerShape(50)
                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Estas en el día $diaActual",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeW    = 14.dp.toPx()
            val inset      = 14.dp.toPx()
            val canvasSize = size.width - (inset * 2)
            val startAngle = 20f
            val sweepTotal = 140f

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

            val sweepSuperados = sweepTotal * ratio
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = startAngle + sweepRestantes,
                sweepAngle = sweepSuperados,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(canvasSize, canvasSize),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )
        }

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