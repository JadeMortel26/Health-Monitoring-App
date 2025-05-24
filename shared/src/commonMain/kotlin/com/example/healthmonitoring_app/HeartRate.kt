package com.example.healthmonitoring_app
import kotlinx.datetime.Clock



data class HeartRate(
    val value : Int = 0,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "value" to value,
            "timestamp" to timestamp
        )
    }
}
