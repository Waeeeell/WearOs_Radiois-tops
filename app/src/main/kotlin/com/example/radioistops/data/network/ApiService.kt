package com.example.radioistops.data.network

import com.example.radioistops.data.model.WatchEstado
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/watch/estado/{cip}")
    suspend fun getEstadoReloj(@Path("cip") cip: String): WatchEstado
}