package com.example.radioistops.presentation.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.radioistops.domain.models.HomeData
import com.example.radioistops.domain.models.UiState
import com.example.radioistops.presentation.viewmodels.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel, onNavigate: (String) -> Unit) {
    val state by viewModel.homeState.collectAsState()
    
    when (val safeState = state) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(indicatorColor = Color(0xFF4CAF50))
            }
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
                Text("Error: ${safeState.message}", color = Color.White)
            }
        }
        is UiState.Success -> {
            HomeUI(data = safeState.data)
        }
    }
}

@Composable
fun HomeUI(data: HomeData) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E)), // Gris ultra oscuro premium (Apple Watch-like)
        contentAlignment = Alignment.Center
    ) {
        // Draw Curved Arcs for progressed treatment
        HomeArcs()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            
            // Battery Status Pill
            Box(
                modifier = Modifier
                    .background(Color(0xFF4CAF50), RoundedCornerShape(12.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = "Battery",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = data.batteryText,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            // Date string
            Text(
                text = data.dateString,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            
            // Large Centered Time
            Text(
                text = data.timeString,
                color = Color.White,
                fontSize = 44.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = (-1).sp
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Dynamic Message
            Text(
                text = data.dynamicMessage,
                color = Color.White,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(14.dp))
            
            // Biometrics Row
            HealthMetricsRow(heartRate = data.heartRateBpm, spo2 = data.spO2Percentage)
        }
    }
}

@Composable
fun HealthMetricsRow(heartRate: Int, spo2: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Heart Rate
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Heartbeat Animation
            val infiniteTransition = rememberInfiniteTransition(label = "heartbeat")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.15f,
                animationSpec = infiniteRepeatable(
                    animation = tween(300, delayMillis = 500, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "heart_scale"
            )
            
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Heart Rate",
                tint = Color(0xFFE53935), // Rojo vibrante médico
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "${heartRate}bpm", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        // Separador Vertical sutil
        Box(modifier = Modifier.height(26.dp).width(1.dp).background(Color.White.copy(alpha=0.4f)))
        Spacer(modifier = Modifier.width(16.dp))
        
        // SpO2
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF3F51B5), CircleShape), // Azul O2
                contentAlignment = Alignment.Center
            ) {
                Text("O₂", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "${spo2}%", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HomeArcs() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidth = 12.dp.toPx()
        val arcPadding = strokeWidth / 2f
        
        // Bottom Left Arc (Teal)
        drawArc(
            color = Color(0xFF00ACC1),
            startAngle = 105f,
            sweepAngle = 60f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            topLeft = Offset(arcPadding, arcPadding)
        )
        
        // Bottom Right Arc (Green)
        drawArc(
            color = Color(0xFF4CAF50),
            startAngle = 15f,
            sweepAngle = 60f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            topLeft = Offset(arcPadding, arcPadding)
        )
    }
}

@Preview(widthDp = 200, heightDp = 200)
@Composable
fun HomePreview() {
    HomeUI(
        data = HomeData(
            batteryText = "72%",
            batteryLevel = 0.72f,
            dateString = "13 de diciembre",
            timeString = "18:31",
            dynamicMessage = "Sal a dar un paseo de 15 minutos por el parque.",
            heartRateBpm = 78,
            spO2Percentage = 95,
            leftProgressionText = "7 días",
            rightProgressionText = "7 días"
        )
    )
}
