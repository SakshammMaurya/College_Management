package com.example.collegemanagement

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collegemanagement.Navigation.NavGraph
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.ui.theme.CollegeManagementTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    private val NOTIFICATION_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        // Request notification permission on Android 13+
        requestNotificationPermission()

        setContent {
            NavGraph(navController = rememberNavController())
        }
        FirebaseMessaging.getInstance().subscribeToTopic("all")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Subscribed to 'all' topic successfully")
                } else {
                    Log.e("FCM", "Subscription failed", task.exception)
                }
            }

        val navigateTo = intent?.extras?.getString("navigateTo")

        if (navigateTo == "notice") {
            Routes.NavigationHandler.navigateToNotice.value = true
        }
    }

//    private fun handleIntent(intent: Intent?) {
//        val navigateTo = intent?.getStringExtra("navigateTo")
//        val noticeId = intent?.getStringExtra("noticeId")
//
//        if (navigateTo != null) {
//            Routes.NavigationHandler.targetScreen.value = navigateTo
//            Routes.NavigationHandler.noticeId.value = noticeId
//        }
//    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }
}

