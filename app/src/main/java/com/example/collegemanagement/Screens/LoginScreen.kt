package com.example.collegemanagement.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.Utils.Constants
import com.example.collegemanagement.Utils.Constants.isAdmin
import com.example.collegemanagement.Viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoginMode by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(12.dp))

        errorMessage?.let {
            Text(it, color = Color.Red)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(onClick = {
            if (isLoginMode) {
                viewModel.login(email, password,
                    onSuccess = {
                        val route = if (Constants.isAdmin) Routes.AdminDashboard.route else Routes.BottomNav.route
                        navController.navigate(route) {
                            popUpTo(Routes.Login.route) { inclusive = true }
                        }
                    },
                    onError = { message -> errorMessage = message }
                )
            } else {
                viewModel.signup(email, password,
                    onSuccess = {
                        val route = if (Constants.isAdmin) Routes.AdminDashboard.route else Routes.BottomNav.route
                        navController.navigate(route) {
                            popUpTo(Routes.Login.route) { inclusive = true }
                        }
                    },
                    onError = { message -> errorMessage = message }
                )
            }
        }) {
            Text(if (isLoginMode) "Login" else "Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            isLoginMode = !isLoginMode
        }) {
            Text(if (isLoginMode) "Don't have an account? Sign Up" else "Already have an account? Login")
        }
    }
}



