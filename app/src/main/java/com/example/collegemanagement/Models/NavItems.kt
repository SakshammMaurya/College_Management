package com.example.collegemanagement.Models

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val title: String,
    val icon: Int,

)

data class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String
)