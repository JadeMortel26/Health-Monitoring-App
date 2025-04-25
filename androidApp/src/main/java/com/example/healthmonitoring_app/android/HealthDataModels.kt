package com.example.healthmonitoring_app.android

data class HeartRate(
    val bmp: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class Sleep(
    val durationInHours: Float = 0f,
    val sleepQuality: String = "",
    val timestamp: Long = System.currentTimeMillis()

)

data class StepCount(
    val steps: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)