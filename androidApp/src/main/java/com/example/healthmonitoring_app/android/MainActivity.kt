package com.example.healthmonitoring_app.android

/*import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
/*import com.example.healthmonitoring_app.HeartRate
import com.example.healthmonitoring_app.android.Sleep
import com.example.healthmonitoring_app.android.StepCount*/
import com.example.healthmonitoring_app.HeartRate
import com.example.healthmonitoring_app.Sleep
import com.example.healthmonitoring_app.StepCount
import com.example.healthmonitoring_app.android.FirestoreRepository
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions

import com.example.healthmonitoring_app.HeartRate
import com.example.healthmonitoring_app.Sleep
import com.example.healthmonitoring_app.StepCount
import kotlinx.datetime.Clock


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user == null) {
            Log.w("BackendInit", "User is not signed in")
            // Optionally: redirect to sign-in activity
            return
        }

        val userId = user.uid
        Log.d("BackendInit", "Signed in as $userId")

        // Optional: Create user profile document on first login
        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
        val profile = mapOf(
            "UserEmail" to user.email,
            "createdAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )
        userRef.set(profile, com.google.firebase.firestore.SetOptions.merge())

        CSVExporter.exportToCSV(
            context = this,
            userId = user.uid
        ) { success ->
            if (success) {
                Log.d("CSVExport", "THe CSV file created successfully!")
            } else {
                Log.e("CSVExport", "Failed to create CSV.")
            }
        }

    }



        // FINISHED TESTING
        /*val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid ?: "unknown"
        val email = "jademortel26@gmail.com"
        val password = "Jadiee28"

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnCompleteListener

                    val heartRateData = HeartRate((60..100).random(), System.currentTimeMillis())
                    val sleepData = Sleep((5..8).random().toFloat(), listOf("Good", "Fair", "Poor").random(), System.currentTimeMillis())
                    val stepCountData = StepCount((5000..15000).random(), System.currentTimeMillis())

                    FirestoreRepository.saveHeartRateData(heartRateData, userId)
                    FirestoreRepository.saveStepCountData(stepCountData, userId)
                    FirestoreRepository.saveSleepData(sleepData, userId)

                    Log.d("BackendTest", "Saved data for userId=$userId")
                } else {
                    Log.w("BackendTest", "Sign in failed", task.exception)
                }*/





        /*else {
                    Log.w("FirebaseAuth", "Sign-in failed", task.exception)
                }*/
            }

            // Test Read for Step Count
        /*FirestoreRepository.getStepCountData(
            userId,
            onSuccess = { stepList ->
                Log.d("FirestoreTest", "Step Count Data: $stepList")
            },
            onFailure = { e ->
                Log.e("FirestoreTest", "Failed to get step count data", e)
            }
        )

        FirestoreRepository.getSleepData(
            userId,
            onSuccess = { sleepList ->
                Log.d("FirestoreTest", "Sleep Data: $sleepList")
            },
            onFailure = { e ->
                Log.e("FirestoreTest", "Failed to get sleep data", e)
            }
        )

        FirestoreRepository.getHeartRateData(
            userId,
            onSuccess = { heartRateList ->
                Log.d("FirestoreTest", "Heart Rate Data: $heartRateList")
            },
            onFailure = { e ->
                Log.e("FirestoreTest", "Failed to get heart rate data", e)
            }
        )*/





        // This is the simple UI
       /* setContent {
            Text("Testing Firebase Auth + Firestore Integration")
        }
    }*/




