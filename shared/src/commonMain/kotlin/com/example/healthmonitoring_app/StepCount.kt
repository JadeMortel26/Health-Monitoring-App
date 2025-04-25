package com.example.healthmonitoring_app
import kotlinx.datetime.Clock



data class StepCount(
    val steps: Int = 0,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()

)