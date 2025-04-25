package com.example.healthmonitoring_app
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Sleep(
    val durationInHours: Float = 0f,
    val sleepQuality: String = "",
    val timestamp: Instant = Clock.System.now()
)