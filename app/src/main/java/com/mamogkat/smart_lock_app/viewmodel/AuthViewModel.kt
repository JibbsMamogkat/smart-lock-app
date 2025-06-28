package com.mamogkat.smart_lock_app.viewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import android.util.Log
import androidx.lifecycle.ViewModel


class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registerNewUser(name: String, onSuccess: () -> Unit) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            Log.e("AuthViewModel", "No logged-in user.")
            return
        }

        val userData = hashMapOf(
            "uid" to currentUser.uid,
            "name" to name,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("users")
            .document(currentUser.uid)
            .set(userData)
            .addOnSuccessListener {
                Log.d("AuthViewModel", "User registered successfully")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "Failed to register user", e)
            }
    }

}