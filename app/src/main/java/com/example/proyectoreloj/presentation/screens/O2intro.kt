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
fun O2introPreview() {
    O2intro(onStartMeasure = {})
}

@Composable
fun O2intro(onStartMeasure: () -> Unit = {}) {
    // Column apila los elementos uno debajo de otro (verticalmente)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro puro
            .padding(horizontal = 12.dp), // Margen a los lados calcado
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 1. Título principal
        Text(
            text = "Oxígeno en sangre",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp)) // Espacio idéntico

        // 2. Imagen central (Icono O2)
        Box(
            modifier = Modifier
                .height(30.dp) // Misma altura de caja que el ECG
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.o2icono),
                contentDescription = "Icono O2",
                // fillMaxHeight para que ocupe la caja pero sin deformarse a lo ancho
                modifier = Modifier.fillMaxHeight()
            )
        }

        Spacer(modifier = Modifier.height(10.dp)) // Espacio idéntico

        // 3. Texto de instrucciones
        Text(
            text = "Ajuste la correa y evite cualquier\nmovimiento para asegurar la lectura.",
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio idéntico

        // 4. Botón azul "Medir"
        Button(
            onClick = { onStartMeasure() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF65D4F9), // Color azul claro de tu diseño
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth(0.4f) // El botón ocupa el 40% del ancho
                .height(22.dp), // Misma altura exacta
            shape = RoundedCornerShape(50) // Bordes redondeados
        ) {
            Text(
                text = "Medir",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}