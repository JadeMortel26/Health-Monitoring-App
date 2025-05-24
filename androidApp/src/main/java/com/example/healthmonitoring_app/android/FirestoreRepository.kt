package com.example.healthmonitoring_app.android
import com.example.healthmonitoring_app.HeartRate
import com.example.healthmonitoring_app.Sleep
import com.example.healthmonitoring_app.StepCount
import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("StaticFieldLeak")
object FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()

    fun saveHeartRateData(heartRate: HeartRate, userId: String) {
        db.collection("users").document(userId).collection("heartRateData").add(heartRate)
    }

    fun saveStepCountData(stepCount: StepCount, userId: String) {
        db.collection("users").document(userId).collection("stepCountData").add(stepCount)
    }

    fun saveSleepData(sleep: Sleep, userId: String) {
        db.collection("users").document(userId).collection("sleepData").add(sleep)
    }



    fun getStepCountData(userId: String, onSuccess: (List<StepCount>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(userId).collection("stepCountData")
            .get()
            .addOnSuccessListener { result ->
                val data = result.documents.mapNotNull { it.toObject(StepCount::class.java) }
                onSuccess(data)
            }
            .addOnFailureListener(onFailure)
    }

    fun getHeartRateData(userId: String, onSuccess: (List<HeartRate>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(userId).collection("heartRateData")
            .get()
            .addOnSuccessListener { result ->
                val data = result.documents.mapNotNull { it.toObject(HeartRate::class.java) }
                onSuccess(data)
            }
            .addOnFailureListener(onFailure)
    }

    fun getSleepData(userId: String, onSuccess: (List<Sleep>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users").document(userId).collection("sleepData")
            .get()
            .addOnSuccessListener { result ->
                val data = result.documents.mapNotNull { it.toObject(Sleep::class.java) }
                onSuccess(data)
            }
            .addOnFailureListener(onFailure)
    }


    /*fun getHeartRateData(
        userId: String,
        onSuccess: (List<HeartRate>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users").document(userId).collection("heartRateData")
            .get()
            .addOnSuccessListener { result ->
                val data = result.documents.mapNotNull { it.toObject(HeartRate::class.java) }
                onSuccess(data)
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getStepCountData(
        userId: String,
        onSuccess: (List<StepCount>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users").document(userId).collection("stepCountData")
            .get()
            .addOnSuccessListener { result ->
                val data = result.documents.mapNotNull { it.toObject(StepCount::class.java) }
                onSuccess(data)
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getSleepData(
        userId: String,
        onSuccess: (List<Sleep>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users").document(userId).collection("sleepData")
            .get()
            .addOnSuccessListener { result ->
                val data = result.documents.mapNotNull { it.toObject(Sleep::class.java) }
                onSuccess(data)
            }
            .addOnFailureListener { onFailure(it) }
    } */
}
