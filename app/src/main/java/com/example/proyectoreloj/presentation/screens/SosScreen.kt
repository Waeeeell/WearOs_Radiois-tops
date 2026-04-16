package com.example.radioistops.presentation.screens



import androidx.compose.animation.core.Animatable

import androidx.compose.animation.core.LinearEasing

import androidx.compose.animation.core.tween

import androidx.compose.foundation.background

import androidx.compose.foundation.gestures.detectTapGestures

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Devices

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.wear.compose.material.CircularProgressIndicator

import androidx.wear.compose.material.Text

import kotlinx.coroutines.launch



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

// Estas variables controlan la animación del anillo

    val progress = remember { Animatable(0f) }

    val coroutineScope = rememberCoroutineScope()

    var isTriggered by remember { mutableStateOf(false) } // Para evitar que se llame dos veces



    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(Color.Black) // Negro puro como en las pantallas anteriores

            .padding(horizontal = 12.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center

    ) {



// 1. Título

        Text(

            text = "¿NECESITAS AYUDA?",

            color = Color.White,

            fontWeight = FontWeight.Bold,

            fontSize = 12.sp,

            textAlign = TextAlign.Center

        )



        Spacer(modifier = Modifier.height(8.dp))



// 2. Subtítulo (Instrucciones)

        Text(

            text = "Mantén pulsado 3 segundos para\ncontactar con emergencias",

            color = Color.White,

            fontWeight = FontWeight.Normal,

            fontSize = 12.sp,

            textAlign = TextAlign.Center,

            lineHeight = 12.sp,





            )



        Spacer(modifier = Modifier.height(14.dp))



// 3. El Botón SOS con el anillo de progreso

        Box(

            modifier = Modifier

                .size(50.dp) // Tamaño total del área interactiva

                .pointerInput(Unit) {

// Aquí detectamos cuando el usuario pone y quita el dedo

                    detectTapGestures(

                        onPress = {

                            if (isTriggered) return@detectTapGestures



// 1. Al presionar, empezamos a llenar el anillo en 3 segundos (3000 ms)

                            val job = coroutineScope.launch {

                                progress.animateTo(

                                    targetValue = 1f,

                                    animationSpec = tween(durationMillis = 3000, easing = LinearEasing)

                                )

// Si la animación termina y llega a 1f sin ser cancelada...

                                if (progress.value == 1f && !isTriggered) {

                                    isTriggered = true

                                    onSosTriggered() // ¡Lanzamos el SOS!

                                }

                            }



// 2. Esperamos a que el usuario levante el dedo

                            try {

                                awaitRelease()

                            } finally {

// 3. Al soltar el dedo, cancelamos la carga y vaciamos el anillo rápido (300 ms)

                                job.cancel()

                                if (!isTriggered) {

                                    coroutineScope.launch {

                                        progress.animateTo(0f, tween(300))

                                    }

                                }

                            }

                        }

                    )

                },

            contentAlignment = Alignment.Center

        ) {

// El Anillo exterior (CircularProgressIndicator)

            CircularProgressIndicator(

                progress = progress.value,

                modifier = Modifier.fillMaxSize(),

                indicatorColor = Color.Red, // El color del progreso

                trackColor = Color.DarkGray, // El color del fondo del anillo

                strokeWidth = 4.dp

            )



// El círculo rojo interior con el texto

            Box(

                modifier = Modifier

                    .size(40.dp) // Un poco más pequeño que el anillo para que respire

                    .background(Color(0xFFD32F2F), CircleShape),

                contentAlignment = Alignment.Center

            ) {

                Text(

                    text = "SOS",

                    color = Color.White,

                    fontSize = 8.sp,

                    fontWeight = FontWeight.Bold

                )

            }

        }

    }

}