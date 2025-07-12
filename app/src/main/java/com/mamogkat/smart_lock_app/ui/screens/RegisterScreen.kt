package com.mamogkat.smart_lock_app.ui.screens

import android.net.wifi.WifiManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mamogkat.smart_lock_app.viewmodel.AuthViewModel
import com.mamogkat.smart_lock_app.viewmodel.LockViewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kotlinx.coroutines.delay


@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    lockViewModel: LockViewModel
) {
    val context = LocalContext.current
    var registrationAllowed by remember { mutableStateOf<Boolean?>(null) }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    // Observe Firebase real-time mode
    DisposableEffect(Unit) {
        lockViewModel.observeRegistrationAllowed {
            registrationAllowed = it
        }
        onDispose {
            lockViewModel.stopObservingRegistrationAllowed()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (registrationAllowed) {
            null -> {
                CircularProgressIndicator()
            }

            false -> {
                Text(
                    text = "Registration is currently disabled.\nAsk the owner to enter the admin code on the keypad.",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back to Login")
                }
            }

            true -> {
                Text("Enter your credentials to register a new user", style = MaterialTheme.typography.headlineSmall)
                Text("You only have 1 minute to register a new user.", fontSize = 12.sp, color = colorResource(id = com.mamogkat.smart_lock_app.R.color.mmcm_red))
                CountdownTimer(onTimerFinish = { registrationAllowed = false })
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                        authViewModel.registerNewUser(name, email, password) { success, error ->
                            if (success) {
                                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, error ?: "Registration failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Register")
                }
            }
        }
    }
}

@Composable
fun CountdownTimer(
    totalTime: Long = 60_000L, // 1 minute in ms
    interval: Long = 1_000L,    // 1 second tick
    onTimerFinish: () -> Unit
) {
    var timeLeft by remember { mutableStateOf(totalTime) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0L) {
            delay(interval)
            timeLeft -= interval
        }
        onTimerFinish()
    }

    val seconds = (timeLeft / 1000).toInt()

    Text(
        text = "Time left: $seconds sec",
        style = MaterialTheme.typography.bodyLarge,
        color = if (seconds <= 10) colorResource(id = com.mamogkat.smart_lock_app.R.color.mmcm_red) else MaterialTheme.colorScheme.onSurface,
    )
}



// ================================= prev code by duff==================================
