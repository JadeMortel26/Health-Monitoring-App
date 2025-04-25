package com.example.healthmonitoring_app.android

/*import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
/*import com.example.healthmonitoring_app.android.HeartRate
import com.example.healthmonitoring_app.android.Sleep
import com.example.healthmonitoring_app.android.StepCount*/
import com.example.healthmonitoring_app.HeartRate
import com.example.healthmonitoring_app.Sleep
import com.example.healthmonitoring_app.StepCount
import com.example.healthmonitoring_app.FirestoreRepository
import kotlinx.datetime.Clock



class   MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        // Test to sign in anonymously
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: "unknown"
                    Log.d("FirebaseAuth", "Signed in anonymously: $userId")

                    /*val testData = hashMapOf(
                        "uid" to userId,
                        "first" to "Jade",
                        "last" to "Mortel",
                        "timestamp" to System.currentTimeMillis()

                    )*/

                    val heartRateData = HeartRate((60..100).random(), Clock.System.now())
                    val sleepData = Sleep((5..8).random().toFloat(), listOf("Good", "Fair", "Poor").random(), Clock.System.now())
                    val stepCountData = StepCount((5000..15000).random(), Clock.System.now())

                    // I'll now try to add the data into the firestore under users collection...

                    db.collection("users")
                        .add(testData)
                        .addOnSuccessListener { docRef -> Log.d("Firestore", "Document added with ID: ${docRef.id}")

                            /*saveSleepData(db, userId)
                            saveStepcountData(db, userId) */

                            FirestoreRepository.saveHeartRateData(heartRateData, userId)
                            FirestoreRepository.saveSleepData(sleepData, userId)
                            FirestoreRepository.saveStepcountData(stepCountData, userId)
                        }


                        .addOnFailureListener { e -> Log.w("Firestore", "Error adding document", e)
                    }

                } else {
                    Log.w("FirebaseAuth", "Sign-in failed", task.exception)
                }

            }

        // Same simple User Interface as before
        setContent {
            Text("Testing the firebase Auth + Firestore")
        }
    }

    private fun saveSleepData(db: FirebaseFirestore, userId: String) {

        val sleep = Sleep(
            durationInHours = (5..8).random().toFloat(),
            sleepQuality = listOf("Good", "Fair", "Poor").random(), // this would simulate 5-8 hours sleep

        )

        db.collection("sleepData")
            .add(sleep)
            .addOnSuccessListener { docRef ->
                Log.d("FirestoreSleep", "Sleep data saved with ID: ${docRef.id}")
            }

            .addOnFailureListener { e ->
                Log.w("FirestoreSleep", "Error saving sleep data", e)

            }
    }

    private fun saveStepcountData(db: FirebaseFirestore, userId: String) {
        val steps = StepCount( steps = (100..10000).random()// this will simulate the randomw steps

        )
        db.collection("stepCountData")
            .add(steps)
            .addOnSuccessListener { docRef ->
                Log.d("FirestoreStepCount", "Step count data saved with ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreStepCount", "Error saving step count data", e)
            }


    }

}*/



import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.google.firebase.auth.FirebaseAuth
import kotlinx.datetime.Clock


import com.example.healthmonitoring_app.Sleep
import com.example.healthmonitoring_app.StepCount
import com.example.healthmonitoring_app.HeartRate



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        //  This is my attempt to sign in anonymously
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: "unknown"
                    Log.d("FirebaseAuth", "Signed in anonymously: $userId")

                    // this methods is used to generate simple data for testing purposes
                    val heartRateData = HeartRate(
                        bpm = (60..100).random(),
                        timestamp = Clock.System.now()
                    )

                    val sleepData = Sleep(
                        durationInHours = (5..8).random().toFloat(),
                        sleepQuality = listOf("Good", "Fair", "Poor").random(),
                        timestamp = Clock.System.now()
                    )

                    val stepCountData = StepCount(
                        steps = (5000..15000).random(),
                        timestamp = Clock.System.now().toEpochMilliseconds()
                    )

                    // THis will save all the data in Firestore
                    FirestoreRepository.saveHeartRateData(heartRateData, userId)
                    FirestoreRepository.saveSleepData(sleepData, userId)
                    FirestoreRepository.saveStepCountData(stepCountData, userId)

                } else {
                    Log.w("FirebaseAuth", "Sign-in failed", task.exception)
                }
            }

        // This is the simple UI
        setContent {
            Text("Testing Firebase Auth + Firestore Integration")
        }
    }
}


