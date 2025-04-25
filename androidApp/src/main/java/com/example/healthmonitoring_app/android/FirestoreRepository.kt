package com.example.healthmonitoring_app.android

import com.example.healthmonitoring_app.HeartRate
import com.example.healthmonitoring_app.Sleep
import com.example.healthmonitoring_app.StepCount
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreRepository {



    private val db = FirebaseFirestore.getInstance()

    fun saveStepCountData(stepCount: StepCount, userId: String) {
        db.collection("users").document(userId)
            .collection("stepCountData")
            .add(stepCount)



    }

    fun saveSleepData(sleep: Sleep, userId: String) {
        db.collection("users").document(userId)
            .collection("sleepData")
            .add(sleep)
    }

    fun saveHeartRateData(heartRate: HeartRate, userId: String) {
        db.collection("users").document(userId)
            .collection("heartRateData")
            .add(heartRate)



    }


}
