package com.example.collegemanagement.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants
import com.example.collegemanagement.ui.theme.LightGreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1500)

        val auth = FirebaseAuth.getInstance().currentUser
        if (auth != null) {
            if (auth.email == "sakshamm1124@gmail.com") {
                Constants.isAdmin = true
                navController.navigate(Routes.AdminDashboard2.route) {
                    popUpTo(Routes.Splash.route) { inclusive = true }
                }
            } else {
                Constants.isAdmin = false
                navController.navigate(Routes.BottomNav.route) {
                    popUpTo(Routes.Splash.route) { inclusive = true }
                }
            }
        } else {
            navController.navigate(Routes.Login.route) {
                popUpTo(Routes.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.splah),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
