package com.example.radioistops.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
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
fun EcgIntroScreenPreview() {
    EcgIntroScreen(onStartEcg = {})
}

@Composable
fun EcgIntroScreen(onStartEcg: () -> Unit) {
    // Column apila los elementos uno debajo de otro (verticalmente)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro puro
            .padding(horizontal = 12.dp), // Margen a los lados para que no toque los bordes
        horizontalAlignment = Alignment.CenterHorizontally, // Centra todo horizontalmente
        verticalArrangement = Arrangement.Center // Centra todo verticalmente en la pantalla
    ) {

        // 1. Título principal
        Text(
            text = "Realizar Electrocardiograma",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 7.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(7.dp)) // Espacio en blanco

        // 2. Imagen del electrocardiograma (Línea ecg)
        Box(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Línea de pulso
            Image(
                painter = painterResource(id = R.drawable.ecg),
                contentDescription = "Línea ECG",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // 3. Texto de advertencia
        Text(
            text = "Esta app NO detecta infartos.\nSi te sientes mal llama al 112 o\npresione SOS.",
            color = Color.White,
            fontSize = 7.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Botón verde "Iniciar"
        Button(
            onClick = { onStartEcg() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF2ECC71), // Color verde exacto de tu imagen
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(0.4f) // El botón ocupa el 70% del ancho
                .height(16.dp),
            shape = RoundedCornerShape(50) // Bordes
        ) {
            Text(
                text = "Iniciar",
                fontWeight = FontWeight.Bold,
                fontSize = 7.sp
            )
        }
    }
}