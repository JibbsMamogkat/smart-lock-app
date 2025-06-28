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


@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val wifiManager = remember {
        context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    // Get the current SSID and remove quotes
    val currentSsid = remember {
        wifiManager.connectionInfo.ssid.replace("\"", "")
    }

    val isConnectedToSmartLock = remember(currentSsid) {
        currentSsid.contains("SmartLock", ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isConnectedToSmartLock) "✅ Connected to SmartLock WiFi" else "Connect to your SmartLock WiFi",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            navController.navigate("login")
        }) {
            Text("Back to Login", fontSize = 16.sp)
        }
    }
}

// ================================= prev code by duff==================================
