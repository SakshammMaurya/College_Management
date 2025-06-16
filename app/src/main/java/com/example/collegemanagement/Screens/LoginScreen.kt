package com.example.collegemanagement.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.R
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
    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }
    var forgotEmail by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nitlogo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(top = 6.dp)
                )
                Text(
                    text = "NIT JAMSHEDPUR",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Column(
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = "Hi!",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = "Welcome",
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = if (!isLoginMode) "Let's create an account" else "Please enter your details",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(70.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            DynamicLoginSection(
                isLoginMode = isLoginMode,
                email = email,
                password = password,
                name = name,
                number = number,
                forgotEmail = forgotEmail,
                showDialog = showDialog,
                errorMessage = errorMessage,
                onLogin = {
                    if(email.isEmpty() || password.isEmpty()){
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()

                    }else {
                        viewModel.login(email, password,
                            onSuccess = {
                                val route =
                                    if (Constants.isAdmin) Routes.AdminDashboard2.route else Routes.BottomNav.route
                                navController.navigate(route) {
                                    popUpTo(Routes.Login.route) { inclusive = true }
                                }
                            },
                            onError = { message -> errorMessage = message }
                        )
                    }
                },
                onSignup = {
                    if(name.isEmpty() || number.isEmpty() || email.isEmpty() || password.isEmpty()){
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        viewModel.signup(email, password, name, number,
                            onSuccess = {
                                val route =
                                    if (Constants.isAdmin) Routes.AdminDashboard2.route else Routes.BottomNav.route
                                navController.navigate(route) {
                                    popUpTo(Routes.Login.route) { inclusive = true }
                                }
                            },
                            onError = { message -> errorMessage = message }
                        )
                    }
                },
                onToggleMode = { isLoginMode = !isLoginMode },
                onForgotPassword = { showDialog = true },
                onForgotEmailChange = { forgotEmail = it },
                onDismissDialog = { showDialog = false },
                onNameChange = {name =it},
                onNumberChange = {number = it},
                onEmailChange = {email = it},
                onPasswordChange = {password = it}
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (isLoginMode) "Don't have an account?" else "Already have an account?",
                color = Color.Gray,
                fontSize = 14.sp
            )
            TextButton(onClick = { isLoginMode = !isLoginMode }) {
                Text(
                    if (isLoginMode) "Sign Up" else "Login",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

    }

}

@Composable
fun DynamicLoginSection(
    isLoginMode: Boolean,
    email: String,
    password: String,
    name: String? = "",
    number: String,
    forgotEmail: String,
    showDialog: Boolean,
    errorMessage: String?,
    onLogin: () -> Unit,
    onSignup: () -> Unit,
    onToggleMode: () -> Unit,
    onForgotPassword: () -> Unit,
    onForgotEmailChange: (String) -> Unit,
    onDismissDialog: () -> Unit,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (!isLoginMode) {
                    TextField(
                        value = name!!,
                        onValueChange = onNameChange,
                        placeholder = { Text("Full name", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Gray,
                            unfocusedIndicatorColor = Color.LightGray,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            cursorColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = number,
                        onValueChange = onNumberChange,
                        placeholder = { Text("Phone number", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Gray,
                            unfocusedIndicatorColor = Color.LightGray,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            cursorColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
        }

        TextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = { Text("Email", color = Color.LightGray) },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        disabledTextColor = Color.Gray
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = { Text("Password", color = Color.LightGray) },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        disabledTextColor = Color.Gray
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

        Row {
            Spacer(modifier = Modifier.weight(1f))
            if (isLoginMode)
                TextButton(onClick = onForgotPassword) {
                    Text("Forgot Password?", color = MaterialTheme.colorScheme.primary)
                }
        }

        Spacer(modifier = Modifier.height(14.dp))

        errorMessage?.let {
            Text(it, color = Color.Red)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(if (!isLoginMode) 20.dp else 0.dp))

        Button(
            onClick = if (isLoginMode) onLogin else onSignup,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoginMode) "Login" else "Sign Up")
        }

        Spacer(modifier = Modifier.height(50.dp))

    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissDialog,
            confirmButton = {
                TextButton(onClick = {
                    // handle password reset
                }) {
                    Text("Send Email")
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Forgot Password?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = forgotEmail,
                        onValueChange = onForgotEmailChange,
                        placeholder = { Text("Enter email", color = Color.LightGray) },

                    )
                }
            },
            dismissButton = {}
        )
    }
}





