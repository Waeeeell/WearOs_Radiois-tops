package com.example.proyectoreloj.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun SosScreen(
    onDismiss: () -> Unit
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
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Title
            Text(
                text = "¿NECESITAS AYUDA?",
                style = MaterialTheme.typography.title1.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                textAlign = TextAlign.Center
            )

            // Description
            Text(
                text = "Deslize el botón rojo para contactar con emergencias y enviar tu ubicación",
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color(0xFFB0B0B0),
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // Buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Cancel button
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF444444)
                    ),
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "✕",
                        style = MaterialTheme.typography.title1.copy(
                            fontSize = 22.sp,
                            color = Color.White
                        )
                    )
                }

                // SOS button
                Button(
                    onClick = { /* Trigger SOS call */ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFE53935)
                    ),
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = "SOS",
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}
