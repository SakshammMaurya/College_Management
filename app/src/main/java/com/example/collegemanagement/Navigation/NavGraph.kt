package com.example.collegemanagement.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collegemanagement.Admin.screens.AdminDashboard
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
import com.example.collegemanagement.Utils.Constants.isAdmin

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {



    NavHost(navController = navController, startDestination = if(isAdmin) Routes.AdminDashboard.route else Routes.BottomNav.route) {
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

    }

}