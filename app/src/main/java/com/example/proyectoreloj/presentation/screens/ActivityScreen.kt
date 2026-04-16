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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun ActivityScreenPreview() {
    ActivityScreen(
        diasSuperados = 6,
        diasRestantes = 8,
        diaActual = 8
    )
}

@Composable
fun ActivityScreen(
    diasSuperados: Int = 6,
    diasRestantes: Int = 8,
    diaActual: Int = 8
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
        onDispose {
            context.unregisterReceiver(batteryReceiver)
        }
    }

    // Calculamos la proporción para la barra curva (igual que HomeScreen)
    val totalDias = diasSuperados + diasRestantes
    val ratio = if (totalDias > 0) diasSuperados.toFloat() / totalDias else 0.5f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Fondo negro como HomeScreen
        contentAlignment = Alignment.Center
    ) {

        // --- 1. CONTENIDO TEXTUAL CENTRAL ---
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Batería (añadida para consistencia con HomeScreen)
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
            Spacer(modifier = Modifier.height(24.dp))
            // Título
            Text(
                text = "Ya vas por la mitad!",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Mensaje central con texto coloreado (AnnotatedString)
            val mensajeCentral = buildAnnotatedString {
                append("Puedes salir a ")
                withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) { // Color Verde
                    append("dar un paseo")
                }
                append(",\npero recuerda; de 15 minutos!")
            }

            Text(
                text = mensajeCentral,
                color = Color.White,
                fontSize = 12.sp, // Tamaño de texto de HomeScreen (mensajeApi)
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp, // LineHeight de HomeScreen
                modifier = Modifier.padding(horizontal = 24.dp) // Padding de HomeScreen
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Estado de día actual
            Text(
                text = "Estás en el dia $diaActual",
                color = Color.White,
                fontSize = 12.sp, // Tamaño pequeño como en HomeScreen
                fontWeight = FontWeight.Bold
            )
        }

        // --- 2. BARRA CURVA DE PROGRESO (Idéntica a HomeScreen) ---
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeW = 14.dp.toPx() // Grosor de HomeScreen
            val inset = 14.dp.toPx()   // Inset de HomeScreen
            val canvasSize = size.width - (inset * 2)

            val startAngle = 20f   // Ángulo de HomeScreen
            val sweepTotal = 140f  // Sweep de HomeScreen

            // Fondo de la barra (Días Superados / Base)
            drawArc(
                color = Color(0xFF2088A5),
                startAngle = startAngle,
                sweepAngle = sweepTotal,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(canvasSize, canvasSize),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )

            // Progreso (Días Restantes / Verde)
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

        // --- 3. TEXTOS EN LOS EXTREMOS DE LA BARRA (Idénticos a HomeScreen) ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotate(152f), // Alineado casi al extremo final
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$diasSuperados dias",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 14.dp) // Forzado hacia el interior para centrarse en el trazo de 10dp
                    .rotate(-90f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotate(28f), // Alineado casi al extremo inicial
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$diasRestantes dias",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 14.dp) // Forzado hacia el interior para centrarse en el trazo de 10dp
                    .rotate(-90f)
            )
        }
    }
}
