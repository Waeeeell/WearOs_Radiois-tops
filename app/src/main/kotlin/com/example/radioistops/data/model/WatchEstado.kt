package com.example.radioistops.data.model

data class WatchEstado(
    val diasSuperados: Int,
    val diasRestantes: Int,
    val diaActual: Int,
    val porcentajeBateria: Int,
    val mensajeApi: String,
    val titulo: String,
    val mensajeParte1: String,
    val mensajeResaltado: String,
    val mensajeParte2: String
)