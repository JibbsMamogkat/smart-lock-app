package com.mamogkat.smart_lock_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mamogkat.smart_lock_app.ui.theme.SmartlockappTheme
import com.mamogkat.smart_lock_app.ui.screens.LoginScreen
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.mamogkat.smart_lock_app.navigation.AppNavHost
import com.mamogkat.smart_lock_app.viewmodel.AuthViewModel
import com.mamogkat.smart_lock_app.viewmodel.LockViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartlockappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val lockViewModel: LockViewModel = viewModel()
                    val authViewModel: AuthViewModel = viewModel()

                    AppNavHost(navController = navController, lockViewModel = lockViewModel, authViewModel = authViewModel)

                   /* FirebaseApp.initializeApp(this)*/
                }

            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartlockappTheme {
        Greeting("Android")
    }
}