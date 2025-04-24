package com.example.collegemanagement.Navigation

sealed class Routes(val route : String) {

    object Home:Routes("Home")

    object Faculty:Routes("Faculty")

    object AboutUs:Routes("About us")

    object BottomNav:Routes("Bottom Navigation")

    object Gallery:Routes("Gallery")

    object AdminDashboard:Routes("Admin_Dashboard")
    object ManageBanner:Routes("Manage_Banner")
    object ManageFaculty:Routes("Manage_Faculty")
    object ManageGallery:Routes("ManageGallery")
    object ManageNotice:Routes("Manage_Notice")
    object ManageCollegeInfo:Routes("Manage_College_Info")
    object FacultyDetailScreen:Routes("faculty_details/{catName}")





}