package com.example.radioistops.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // En emulador Android usa 10.0.2.2 en vez de localhost
    // En reloj físico conectado por WiFi usa tu IP local (ej: 192.168.1.X)
    private const val BASE_URL = "https://web-radioisotopo-api.onrender.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}