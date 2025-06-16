package com.example.collegemanagement.Navigation

import android.window.SplashScreen
import androidx.compose.runtime.mutableStateOf

sealed class Routes(val route : String) {

    object Home:Routes("Home")

    object Faculty:Routes("Faculty")

    object AboutUs:Routes("About us")

    object BottomNav:Routes("Bottom Navigation")

    object Gallery:Routes("Gallery")

    object AdminDashboard2:Routes("Admin_Dashboard2")
    object ManageBanner:Routes("Manage_Banner")
    object ManageFaculty:Routes("Manage_Faculty")
    object ManageGallery:Routes("ManageGallery")
    object ManageNotice:Routes("Manage_Notice")
    object ManageCollegeInfo:Routes("Manage_College_Info")
    object FacultyDetailScreen:Routes("faculty_details/{catName}")

    object Login : Routes("Login")
    object ChangePassword : Routes("ChangePassword")

    object Splash : Routes("splash")

    object NavigationHandler {
        val navigateToNotice = mutableStateOf(false)
    }

}