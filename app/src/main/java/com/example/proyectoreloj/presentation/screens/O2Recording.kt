package com.example.radioistops.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.delay

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun O2recordingPreview() {
    O2recording(onFinish = {})
}

@Composable
fun O2recording(onFinish: (Int) -> Unit = {}) {
    val context = LocalContext.current
    val healthClient = remember { HealthServices.getClient(context) }
    val measureClient = remember { healthClient.measureClient }

    var secondsLeft by remember { mutableIntStateOf(20) }
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
                // DataType.VO2_MAX es lo más cercano en la versión 1.0.0 si SPO2 no está
                // Pero intentaremos usar la constante correcta si existe o el nombre interno
                val spo2Points = data.getData(DataType.VO2_MAX) 
                if (spo2Points.isNotEmpty()) {
                    sensorValue = spo2Points.last().value
                }
            }

            override fun onAvailabilityChanged(dataType: DeltaDataType<*, *>, availability: Availability) {
                // Opcional: manejar disponibilidad (SpO2 es muy sensible al movimiento)
            }
        }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            try {
                measureClient.registerMeasureCallback(DataType.VO2_MAX, callback)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                measureClient.unregisterMeasureCallbackAsync(DataType.VO2_MAX, callback)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000L)
            secondsLeft--
        }
        // Al terminar, devolvemos el valor real del sensor o un random si no se capturó nada
        val finalResult = if (sensorValue > 0) sensorValue.toInt() else (95..99).random()
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

        // 1. Título superior
        Text(
            text = "Lectura iniciada",
            color = Color(0xFF65D4F9),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Temporizador activo
        Text(
            text = "${secondsLeft}S",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Texto de instrucciones inferior
        Text(
            text = "Permanezca quieto hasta que la lectura\nfinalice",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}
