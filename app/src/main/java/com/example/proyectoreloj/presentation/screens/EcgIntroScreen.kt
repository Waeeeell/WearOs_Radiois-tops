package com.example.proyectoreloj.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun EcgIntroScreen(
    onStartEcg: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Title
            Text(
                text = "Realizar Electrocardiograma",
                style = MaterialTheme.typography.title2.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                ),
                textAlign = TextAlign.Center
            )

            // ECG waveform animation placeholder
            EcgWaveform(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )

            // Warning text
            Text(
                text = "Esta app NO detecta infartos.\nSi te sientes mal llama al 112 o\npresione SOS.",
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFFE53935),
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center
            )

            // Start button
            Button(
                onClick = { onStartEcg() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4CAF50)
                ),
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(0.7f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Iniciar",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun EcgWaveform(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val midY = height / 2

        val path = Path().apply {
            moveTo(0f, midY)

            // Simulate an ECG-like waveform with multiple peaks
            val segmentWidth = width / 5f

            for (i in 0 until 5) {
                val startX = i * segmentWidth
                // Flat baseline
                lineTo(startX + segmentWidth * 0.15f, midY)
                // Small P wave
                lineTo(startX + segmentWidth * 0.2f, midY - height * 0.08f)
                lineTo(startX + segmentWidth * 0.25f, midY)
                // Flat
                lineTo(startX + segmentWidth * 0.3f, midY)
                // Q dip
                lineTo(startX + segmentWidth * 0.35f, midY + height * 0.1f)
                // R peak (tall)
                lineTo(startX + segmentWidth * 0.42f, midY - height * 0.4f)
                // S dip
                lineTo(startX + segmentWidth * 0.5f, midY + height * 0.15f)
                // Return to baseline
                lineTo(startX + segmentWidth * 0.55f, midY)
                // T wave
                lineTo(startX + segmentWidth * 0.7f, midY - height * 0.12f)
                lineTo(startX + segmentWidth * 0.8f, midY)
                // Baseline to end
                lineTo(startX + segmentWidth, midY)
            }
        }

        drawPath(
            path = path,
            color = Color(0xFFE53935),
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
