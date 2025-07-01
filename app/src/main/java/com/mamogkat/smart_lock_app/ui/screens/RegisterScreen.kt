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

    // Fetch registration mode once
    LaunchedEffect(Unit) {
        lockViewModel.isRegistrationAllowed {
            registrationAllowed = it
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
                Text("Enter your name to register:")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

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


// ================================= prev code by duff==================================
