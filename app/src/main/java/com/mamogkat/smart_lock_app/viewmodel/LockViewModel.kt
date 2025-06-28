package com.mamogkat.smart_lock_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.mamogkat.smart_lock_app.data.SmartLockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LockViewModel : ViewModel() {
    private val repository = SmartLockRepository()

    private val _status = MutableStateFlow("Loading...")
    val status: StateFlow<String> = _status

    fun observeLockStatus() {
        FirebaseFirestore.getInstance()
            .collection("smart_lock")
            .document("status")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || !snapshot.exists()) {
                    _status.value = "Failed to load"
                    Log.e("LockViewModel", "Error listening to status", error)
                    return@addSnapshotListener
                }

                val isLocked = snapshot.getBoolean("isLocked") ?: false
                val isOnline = snapshot.getBoolean("isOnline") ?: false
                val alert = snapshot.getString("alert") ?: "none"
                val mode = snapshot.getString("mode") ?: "normal"

                _status.value = when {
                    mode == "registration" -> "🔐 Registration Mode Enabled"
                    alert == "tamper" -> "⚠️ Tamper Detected!"
                    !isOnline -> "Lock is Offline"
                    isLocked -> "Lock is Locked"
                    else -> "Lock is Unlocked"
                }
            }
    }

    //add unlock logic
    private val _commandStatus = MutableStateFlow<String?>(null)
    val commandStatus: StateFlow<String?> = _commandStatus

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


}