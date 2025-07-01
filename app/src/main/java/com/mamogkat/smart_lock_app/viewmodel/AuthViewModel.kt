package com.mamogkat.smart_lock_app.viewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import android.util.Log
import androidx.lifecycle.ViewModel


class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    fun registerNewUser(name: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userMap = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "createdAt" to FieldValue.serverTimestamp()
                        )

                        db.collection("users").document(uid).set(userMap)
                            .addOnSuccessListener {
                                onResult(true, null)
                            }
                            .addOnFailureListener { e ->
                                onResult(false, "Failed to save user: ${e.message}")
                            }
                    } else {
                        onResult(false, "User UID is null")
                    }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

}