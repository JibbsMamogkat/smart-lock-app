package com.mamogkat.smart_lock_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.mamogkat.smart_lock_app.data.SmartLockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LockViewModel : ViewModel() {

    private val repository = SmartLockRepository()
    private val database = FirebaseDatabase.getInstance().reference

    private val _status = MutableStateFlow("Loading...")
    val status: StateFlow<String> = _status

    private val _commandStatus = MutableStateFlow<String?>(null)
    val commandStatus: StateFlow<String?> = _commandStatus

    fun observeLockStatus() {
        database.child("smart_lock").child("status")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val isLocked = snapshot.child("isLocked").getValue(Boolean::class.java) ?: false
                    val isOnline = snapshot.child("isOnline").getValue(Boolean::class.java) ?: false
                    val alert = snapshot.child("alert").getValue(String::class.java) ?: "none"
                    val mode = snapshot.child("mode").getValue(String::class.java) ?: "normal"

                    _status.value = when {
                        mode == "registration" -> "🔐 Registration Mode Enabled"
                        alert == "tamper" -> "⚠️ Tamper Detected!"
                        !isOnline -> "Lock is Offline"
                        isLocked -> "Lock is Locked"
                        else -> "Lock is Unlocked"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _status.value = "Failed to load"
                    Log.e("LockViewModel", "Error listening to RealtimeDB", error.toException())
                }
            })
    }

    fun sendUnlock() {
        viewModelScope.launch {
            val result = repository.sendUnlockCommand()
            _commandStatus.value = if (result) "✅ Unlock command sent!" else "❌ Failed to send unlock"
        }
    }

    fun sendLock() {
        viewModelScope.launch {
            val result = repository.sendLockCommand()
            _commandStatus.value = if (result) "🔒 Lock command sent!" else "❌ Failed to send lock"
        }
    }

//    fun isRegistrationAllowed(onResult: (Boolean) -> Unit) {
//        database.child("smart_lock").child("status")
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val mode = snapshot.child("mode").getValue(String::class.java)
//                    onResult(mode == "registration")
//                }
//                override fun onCancelled(error: DatabaseError) {
//                    onResult(false)
//                }
//            })
//    }
private var registrationListener: ValueEventListener? = null

    fun observeRegistrationAllowed(onResult: (Boolean) -> Unit) {
        val statusRef = database.child("smart_lock").child("status")

        // Detach previous listener (optional but safer)
        registrationListener?.let { statusRef.removeEventListener(it) }

        registrationListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mode = snapshot.child("mode").getValue(String::class.java)
                onResult(mode == "registration")
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(false)
            }
        }

        statusRef.addValueEventListener(registrationListener!!)
    }

    fun stopObservingRegistrationAllowed() {
        registrationListener?.let {
            database.child("smart_lock").child("status").removeEventListener(it)
            registrationListener = null
        }
    }
}
