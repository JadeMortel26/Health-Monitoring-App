package com.example.healthmonitoring_app
import kotlinx.datetime.Instant

data class Sleep(
    val durationInHours: Float = 0f,
    val sleepQuality: String = "",
    val timestamp: Long = 0L
) {
    fun getInstant(): Instant = Instant.fromEpochMilliseconds(timestamp)
}