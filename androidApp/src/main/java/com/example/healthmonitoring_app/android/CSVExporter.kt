package com.example.healthmonitoring_app.android

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.healthmonitoring_app.*
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object CSVExporter {

    private fun Long.toDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(this))
    }

    fun exportToCSV(
        context: Context,
        userId: String,
        onComplete: (Boolean) -> Unit
    ) {
        val allData = mutableListOf<String>()
        allData.add("Type,Value,Quality,Timestamp")

        var completedCalls = 0
        val totalCalls = 3

        fun checkAndFinish() {
            completedCalls++
            if (completedCalls == totalCalls) {
                try {
                    val fileName = "health_data.csv"
                    val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(dir, fileName)
                    FileWriter(file).use { writer ->
                        allData.forEach { writer.write("$it\n") }
                    }
                    Log.d("CSVExport", "Exported to ${file.absolutePath}")
                    onComplete(true)
                } catch (e: Exception) {
                    Log.e("CSVExport", "Failed to write CSV", e)
                    onComplete(false)
                }
            }
        }

        FirestoreRepository.getHeartRateData(userId, { heartRates ->
            heartRates.forEach {
                allData.add("HeartRate,${it.value},,${it.timestamp.toDateString()}")
            }
            checkAndFinish()
        }, { checkAndFinish() })

        FirestoreRepository.getSleepData(userId, { sleeps ->
            sleeps.forEach {
                allData.add("Sleep,${it.durationInHours},${it.sleepQuality},${it.timestamp.toDateString()}")
            }
            checkAndFinish()
        }, { checkAndFinish() })

        FirestoreRepository.getStepCountData(userId, { steps ->
            steps.forEach {
                allData.add("StepCount,${it.steps},,${it.timestamp.toDateString()}")
            }

            checkAndFinish()
        }, { checkAndFinish() })
    }
}
