package com.mamogkat.smart_lock_app.ui.screens

import android.content.Context
import android.net.wifi.WifiManager
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mamogkat.smart_lock_app.viewmodel.AuthViewModel

@Composable
fun RegisterUserScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    @Composable
    fun RegisterUserScreen(
        navController: NavController,
        authViewModel: AuthViewModel
    ) {
        val context = LocalContext.current

        // Detect WiFi Connection
        val wifiManager = remember {
            context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        }
        val currentSsid = remember {
            wifiManager.connectionInfo.ssid.replace("\"", "")
        }
        val isConnectedToSmartLock = remember(currentSsid) {
            currentSsid.contains("SmartLock", ignoreCase = true)
        }

        // State for user name input
        var name by rememberSaveable { mutableStateOf("") }

        // UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = if (isConnectedToSmartLock) "✅ Connected to SmartLock WiFi" else "❌ Connect to your SmartLock WiFi",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isConnectedToSmartLock) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Enter new user's name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (name.isNotBlank()) {
                        authViewModel.registerNewUser(name) {
                            Toast.makeText(context, "✅ User Registered!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    } else {
                        Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Register User")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = {
                navController.navigate("login")
            }) {
                Text("Back to Login")
            }
        }
    }



}

//val context = LocalContext.current
//val wifiManager = remember {
//    context.getSystemService(Context.WIFI_SERVICE) as WifiManager
//}
//
//// Get the current SSID and remove quotes
//val currentSsid = remember {
//    wifiManager.connectionInfo.ssid.replace("\"", "")
//}
//
//val isConnectedToSmartLock = remember(currentSsid) {
//    currentSsid.contains("SmartLock", ignoreCase = true)
//}
//
//Column(
//modifier = Modifier
//.fillMaxSize()
//.padding(24.dp),
//verticalArrangement = Arrangement.Center,
//horizontalAlignment = Alignment.CenterHorizontally
//) {
//    Text(
//        text = if (isConnectedToSmartLock) "✅ Connected to SmartLock WiFi" else "Connect to your SmartLock WiFi",
//        fontSize = 16.sp
//    )
//    Spacer(modifier = Modifier.height(10.dp))
//    Button(onClick = {
//        navController.navigate("login")
//    }) {
//        Text("Back to Login", fontSize = 16.sp)
//    }
//}