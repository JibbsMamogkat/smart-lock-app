package com.mamogkat.smart_lock_app.data
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class SmartLockRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getLockStatus(): Map<String, Any>? {
        return try {
            val snapshot = firestore.collection("smart_lock")
                .document("status")
                .get()
                .await()
            snapshot.data
        } catch (e: Exception) {
            null
        }
    }
    suspend fun sendUnlockCommand(): Boolean {
        return try {
            FirebaseFirestore.getInstance()
                .collection("commands")
                .document("control")
                .set(mapOf("unlock_request" to true))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
    suspend fun sendLockCommand(): Boolean {
        return try {
            FirebaseFirestore.getInstance()
                .collection("commands")
                .document("control")
                .set(mapOf("lock_request" to true), SetOptions.merge())
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }


}