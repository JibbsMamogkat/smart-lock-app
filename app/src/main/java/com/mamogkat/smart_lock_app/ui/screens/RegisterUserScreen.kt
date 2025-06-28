package com.mamogkat.smart_lock_app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mamogkat.smart_lock_app.viewmodel.AuthViewModel

@Composable
fun RegisterUserScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var name by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register New User", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (name.isNotBlank()) {
                authViewModel.registerNewUser(name) {
                    Toast.makeText(context, "User Registered!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            } else {
                Toast.makeText(context, "Enter a name", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit")
        }
    }


}