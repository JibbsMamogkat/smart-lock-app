package com.mamogkat.smart_lock_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.mamogkat.smart_lock_app.viewmodel.LockViewModel


@Composable
fun DashboardScreen(navController: NavController, viewModel: LockViewModel) {
    val status by viewModel.status.collectAsState()
    val commandStatus by viewModel.commandStatus.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeLockStatus()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Smart Lock Status", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = status, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        if (status == "🔓 Registration Mode Enabled") {
            Button(onClick = {
                navController.navigate("register_user")
            }) {
                Text("Register New User")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.sendUnlock() }) {
            Text("Unlock")
        }

        commandStatus?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.sendLock() }) {
            Text("Lock")
        }
    }
}