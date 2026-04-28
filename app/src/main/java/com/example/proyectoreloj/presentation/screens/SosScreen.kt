package com.example.radioistops.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.example.radioistops.R
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = false,
    backgroundColor = 0xFF000000,
    showBackground = true
)
@Composable
fun SosScreenPreview() {
    SosScreen(onSosTriggered = {})
}

@Composable
fun SosScreen(onSosTriggered: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "¿NECESITAS AYUDA?",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Subtítulo
        Text(
            text = "Desliza el botón rojo hacia\nla derecha para contactar\ncon emergencias",
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Componente personalizado: Deslizar para SOS (INVERTIDO)
        SwipeRightToSos(onSosTriggered = onSosTriggered)
    }
}

@Composable
fun SwipeRightToSos(onSosTriggered: () -> Unit) {
    val trackWidth = 140.dp
    val thumbSize = 50.dp

    // Convertimos las medidas a píxeles para la animación matemática
    val maxDragPx = with(LocalDensity.current) { (trackWidth - thumbSize).toPx() }

    // INVERSIÓN 1: El botón empieza a la izquierda del todo (offset = 0)
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var isTriggered by remember { mutableStateOf(false) }

    // Contenedor "píldora" (El carril oscuro)
    Box(
        modifier = Modifier
            .width(trackWidth)
            .height(thumbSize)
            .background(Color(0xFF333333), RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        // INVERSIÓN 2: Texto de fondo en el carril apuntando a la derecha
        Text(
            text = "DESLIZA >>>",
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 55.dp), // Empuja el texto hacia la derecha para que no lo tape el botón inicial
            textAlign = TextAlign.Start
        )

        // El Botón Rojo (Thumb) que se arrastra
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .size(thumbSize)
                .background(Color(0xFFD32F2F), CircleShape)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (isTriggered) return@detectHorizontalDragGestures

                            coroutineScope.launch {
                                // INVERSIÓN 3: Si el usuario lo ha arrastrado más del 70% hacia la derecha
                                if (offsetX.value > maxDragPx * 0.7f) {
                                    // Animamos hasta el borde final de la derecha y disparamos el SOS
                                    offsetX.animateTo(maxDragPx)
                                    isTriggered = true
                                    onSosTriggered()
                                } else {
                                    // Si no llegó lo suficientemente lejos, el botón vuelve a la izquierda (0)
                                    offsetX.animateTo(0f)
                                }
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            // *Nota: Si te vuelve a dar error en versiones antiguas de Compose, usa change.consumePositionChange()
                            change.consume()
                            if (!isTriggered) {
                                coroutineScope.launch {
                                    // Calculamos la nueva posición asegurándonos de no salirnos de los límites
                                    val newOffset = (offsetX.value + dragAmount).coerceIn(0f, maxDragPx)
                                    offsetX.snapTo(newOffset)
                                }
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.llamar),
                contentDescription = "SOS",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}