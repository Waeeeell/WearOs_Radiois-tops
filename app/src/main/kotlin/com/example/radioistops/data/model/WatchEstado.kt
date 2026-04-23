package com.example.radioistops.data.model

import com.google.gson.annotations.SerializedName

data class WatchEstado(
    @SerializedName("diasSuperados") val diasSuperados: Int,
    @SerializedName("diasRestantes") val diasRestantes: Int,
    @SerializedName("diaActual") val diaActual: Int,
    @SerializedName("porcentajeBateria") val porcentajeBateria: Int?,
    @SerializedName("mensajeApi") val mensajeApi: String,
    
    // ESTE ES EL CAMPO CLAVE QUE FALTABA
    @SerializedName("instrucciones") val instrucciones: List<String>? = emptyList(),
    
    // Legacy (si aún los usas en algún lado)
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("mensajeParte1") val mensajeParte1: String?,
    @SerializedName("mensajeResaltado") val mensajeResaltado: String?,
    @SerializedName("mensajeParte2") val mensajeParte2: String?
)