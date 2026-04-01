package com.example.radioistops.domain.models

data class HomeData(
    val batteryText: String,
    val batteryLevel: Float, // for future gradient or fill logic (0f-1f)
    val dateString: String,
    val timeString: String,
    val dynamicMessage: String,
    val heartRateBpm: Int,
    val spO2Percentage: Int,
    val leftProgressionText: String,
    val rightProgressionText: String
)
