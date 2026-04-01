package com.example.proyectoreloj.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.proyectoreloj.presentation.viewmodel.MotivationalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ActivityScreen(
    motivationalViewModel: MotivationalViewModel
) {
    // ── Collect the dynamic activity message & treatment day from the ViewModel ──
    val activityMessage by motivationalViewModel.currentActivityMessage.collectAsState()
    val treatmentDay by motivationalViewModel.treatmentDay.collectAsState()

    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // ── Progress arc ────────────────────────────────────────────────
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val strokeWidth = 10.dp.toPx()
            val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)

            // Blue arc — left
            drawArc(
                color = Color(0xFF2196F3),
                startAngle = 150f,
                sweepAngle = 55f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Teal arc — bottom
            drawArc(
                color = Color(0xFF26C6DA),
                startAngle = 205f,
                sweepAngle = 55f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Green arc — right
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = 345f,
                sweepAngle = 55f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // ── Arc labels ──────────────────────────────────────────────────
        Text(
            text = "6 días",
            style = MaterialTheme.typography.caption1.copy(
                fontSize = 9.sp,
                color = Color(0xFF2196F3)
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp, top = 40.dp)
        )

        Text(
            text = "8 días",
            style = MaterialTheme.typography.caption1.copy(
                fontSize = 9.sp,
                color = Color(0xFF4CAF50)
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp, top = 40.dp)
        )

        // ── Main content ────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Time
            Text(
                text = currentTime,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 14.sp,
                    color = Color(0xFFB0B0B0)
                )
            )

            // ── Dynamic activity message (reads from ViewModel) ─────────
            // Tap to cycle to the next progress message
            Text(
                text = activityMessage,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickable { motivationalViewModel.nextActivityMessage() }
            )

            // Start Activity button
            Button(
                onClick = { /* Start activity tracking */ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF26C6DA)
                ),
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(0.7f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Iniciar actividad",
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }

            // Treatment day counter
            Text(
                text = "Estás en el día $treatmentDay",
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 13.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
