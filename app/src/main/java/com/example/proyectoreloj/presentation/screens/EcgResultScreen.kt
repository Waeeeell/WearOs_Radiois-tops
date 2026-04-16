package com.example.radioistops.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.radioistops.R

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun EcgResultScreenPreview() {
    EcgResultScreen(result = 72, onFinish = {})
}

@Composable
fun EcgResultScreen(result: Int, onFinish: () -> Unit) {
    val (statusText, statusColor) = when {
        result < 50 -> "Frecuencia baja" to Color.Yellow
        result <= 100 -> "Frecuencia normal" to Color(0xFF2ECC71) // Verde
        else -> "Frecuencia alta" to Color.Red
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 1. Título principal
        Text(
            text = "Resultado",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(7.dp))

        // 2. Imagen del electrocardiograma
        Box(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ecg),
                contentDescription = "Línea ECG",
                modifier = Modifier.fillMaxSize()
            )
        }

//        Spacer(modifier = Modifier.height(6.dp))

        // 3. Texto del resultado
        Text(
            text = "$result BPM - $statusText",
            color = statusColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Botón verde "Finalizar"
        Button(
            onClick = onFinish,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF2ECC71),
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(25.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Finalizar",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}
