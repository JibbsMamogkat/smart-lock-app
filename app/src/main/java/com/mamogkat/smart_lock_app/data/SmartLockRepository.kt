package com.mamogkat.smart_lock_app.data
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import com.google.firebase.database.*

class SmartLockRepository {

    private val database = FirebaseDatabase.getInstance().reference

    suspend fun getLockStatus(): Map<String, Any>? {
        return try {
            val snapshot = database.child("smart_lock").child("status").get().await()
            snapshot.value as? Map<String, Any>
        } catch (e: Exception) {
            null
        }
    }

    suspend fun sendUnlockCommand(): Boolean {
        return try {
            database.child("smart_lock").child("command").setValue("unlock").await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun sendLockCommand(): Boolean {
        return try {
            database.child("smart_lock").child("command").setValue("lock").await()
            true
        } catch (e: Exception) {
            false
        }
    }
}