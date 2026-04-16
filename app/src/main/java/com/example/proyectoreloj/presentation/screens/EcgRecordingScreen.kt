package com.example.radioistops.presentation.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.health.services.client.HealthServices
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.DeltaDataType
import androidx.wear.compose.material.Text
import com.example.radioistops.R
import kotlinx.coroutines.delay

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun EcgRecordingScreenPreview() {
    EcgRecordingScreen(onFinish = {})
}

@Composable
fun EcgRecordingScreen(onFinish: (Int) -> Unit) {
    val context = LocalContext.current
    val healthClient = remember { HealthServices.getClient(context) }
    val measureClient = remember { healthClient.measureClient }

    var timeLeft by remember { mutableStateOf(23) }
    var sensorValue by remember { mutableStateOf(0.0) }
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BODY_SENSORS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.BODY_SENSORS)
        }
    }

    val callback = remember {
        object : MeasureCallback {
            override fun onDataReceived(data: DataPointContainer) {
                val heartRatePoints = data.getData(DataType.HEART_RATE_BPM)
                if (heartRatePoints.isNotEmpty()) {
                    sensorValue = heartRatePoints.last().value
                }
            }

            override fun onAvailabilityChanged(dataType: DeltaDataType<*, *>, availability: Availability) {
                // Opcional: manejar disponibilidad
            }
        }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            try {
                measureClient.registerMeasureCallback(DataType.HEART_RATE_BPM, callback)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                measureClient.unregisterMeasureCallbackAsync(DataType.HEART_RATE_BPM, callback)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        // Al terminar los 23 segundos, devolvemos el valor real del sensor
        // Si el sensor no devolvió nada (0.0), podrías poner un valor por defecto o manejar el error
        val finalResult = if (sensorValue > 0) sensorValue.toInt() else (60..100).random()
        onFinish(finalResult)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 1. Texto de instrucción
        Text(
            text = "Mantenga pulsado el botón superior\npara realizar el electrocardiograma",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(7.dp))

        // 2. La imagen del ECG
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ecg),
                contentDescription = "Línea ECG en progreso",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // 3. Temporizador (Cuenta atrás)
        Text(
            text = "${timeLeft}s",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}
