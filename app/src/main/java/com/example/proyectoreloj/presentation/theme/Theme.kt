package com.example.proyectoreloj.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val WearColors = Colors(
    primary = Color(0xFF4CAF50),       // Green accent — matches the screenshots
    primaryVariant = Color(0xFF388E3C),
    secondary = Color(0xFF26C6DA),      // Cyan/teal accent
    secondaryVariant = Color(0xFF0097A7),
    background = Color.Black,
    surface = Color(0xFF1A1A1A),
    error = Color(0xFFE53935),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFB0B0B0),
    onError = Color.White
)

val WearTypography = Typography(
    display1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        letterSpacing = (-0.5).sp,
        color = Color.White
    ),
    title1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.White
    ),
    title2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Color.White
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Color.White
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = Color(0xFFB0B0B0)
    ),
    caption1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Color(0xFF9E9E9E)
    )
)

@Composable
fun ProyectoRelojTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = WearColors,
        typography = WearTypography,
        content = content
    )
}
