package com.mamogkat.smart_lock_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController: NavController, modifier: Modifier = Modifier) {
    val isConnected = false
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isConnected) {
            Text("Connect to your smartlock wifi", fontSize = 16.sp)
        }
        else {
            Text("Youre Connected", fontSize = 16.sp)
        }
        Spacer(modifier.height(10.dp))
        Button(
            onClick = {navController.navigate("login")}
        ) {
            Text(
                text = "back to login",
                /* color = colorResource(id = R.color.mmcm_black),*/
                fontSize = 16.sp
            )
        }

    }
}