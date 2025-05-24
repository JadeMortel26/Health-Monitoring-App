package com.example.healthmonitoring_app
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant


data class StepCount(
    val steps: Int = 0,
    val timestamp: Long = 0L // <== store as primitive for Firestore
) {
    fun getInstant(): Instant = Instant.fromEpochMilliseconds(timestamp)
}