package com.example.radioistops.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun O2ResultScreenPreview() {
    O2ResultScreen(result = 98, onFinish = {})
}

@Composable
fun O2ResultScreen(result: Int, onFinish: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Resultado",
            color = Color(0xFF65D4F9),
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "$result%",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "SpO2",
            color = Color.White,
            fontSize = 8.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { onFinish() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF65D4F9),
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(16.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Volver",
                fontWeight = FontWeight.Bold,
                fontSize = 7.sp
            )
        }
    }
}
