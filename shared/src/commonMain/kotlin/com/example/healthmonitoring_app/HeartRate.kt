package com.example.healthmonitoring_app
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class HeartRate(
    val bpm: Int = 0,
    val timestamp: Instant = Clock.System.now()
)