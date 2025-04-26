package com.example.collegemanagement.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collegemanagement.Admin.screens.AdminDashboard
import com.example.collegemanagement.Admin.screens.AdminDashboard2
import com.example.collegemanagement.Admin.screens.FacultyDetailScreen
import com.example.collegemanagement.Admin.screens.ManageBanner
import com.example.collegemanagement.Admin.screens.ManageCollegeInfo
import com.example.collegemanagement.Admin.screens.ManageFaculty
import com.example.collegemanagement.Admin.screens.ManageGallery
import com.example.collegemanagement.Admin.screens.ManageNotice
import com.example.collegemanagement.Screens.AboutUs
import com.example.collegemanagement.Screens.BottomNav
import com.example.collegemanagement.Screens.Faculty
import com.example.collegemanagement.Screens.Gallery
import com.example.collegemanagement.Screens.Home
import com.example.collegemanagement.Screens.LoginScreen
import com.example.collegemanagement.Utils.Constants
import com.example.collegemanagement.Utils.Constants.isAdmin
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    val auth = FirebaseAuth.getInstance().currentUser
    val startDestination = if (auth != null) {
        if (auth?.email == "sakshamm1124@gmail.com") {
            Constants.isAdmin = true
            Routes.AdminDashboard.route
        } else {
            Constants.isAdmin = false
            Routes.BottomNav.route
        }
    } else {
        Routes.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.BottomNav.route) {
            BottomNav(navController)
        }
        composable(Routes.Home.route) {
            Home(navController)
        }
        composable(Routes.Faculty.route) {
            Faculty(navController)
        }
        composable(Routes.Gallery.route) {
            Gallery(navController)
        }
        composable(Routes.AboutUs.route) {
            AboutUs(navController)
        }
        composable(Routes.ManageNotice.route) {
            ManageNotice(navController)
        }

        composable(Routes.AdminDashboard.route) {
//            AdminDashboard2(navController)
            AdminDashboard(navController)
        }
        composable(Routes.ManageBanner.route) {
            ManageBanner(navController)
        }
        composable(Routes.ManageFaculty.route) {
            ManageFaculty(navController)
        }
        composable(Routes.ManageGallery.route) {
            ManageGallery(navController)
        }
        composable(Routes.ManageCollegeInfo.route) {
            ManageCollegeInfo(navController)
        }
        composable(Routes.FacultyDetailScreen.route) {
            val data = it.arguments!!.getString("catName")
            FacultyDetailScreen(navController,data!!)
        }
        composable(Routes.Login.route) {
            LoginScreen(navController, viewModel())
        }

    }

}