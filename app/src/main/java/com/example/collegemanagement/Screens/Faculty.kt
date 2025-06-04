package com.example.collegemanagement.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.collegemanagement.ItemView.FacultyItemView
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.Viewmodel.FacultyViewModel
import com.example.collegemanagement.ui.theme.LightOrange

@Composable
fun Faculty(navController: NavController) {

    val facultyViewModel: FacultyViewModel = viewModel()

    val categoryList by facultyViewModel.categoryList.observeAsState(null)

    facultyViewModel.getCategory()
    val teacherCountMap by facultyViewModel.categoryTeacherCount.observeAsState(mapOf())

    LaunchedEffect(Unit) {
        facultyViewModel.loadCategoryTeacherCounts()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "Departments",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    )
        Spacer(modifier = Modifier.height(8.dp))
    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
        items(categoryList ?: emptyList()) { catName ->
            val teacherCount = teacherCountMap[catName] ?: 0

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .height(100.dp)
                    .fillMaxWidth()
                    .clickable {
                        val route = Routes.FacultyDetailScreen.route.replace(
                            "{catName}",
                            catName
                        )
                        navController.navigate(route)
                    },
                colors = CardDefaults.cardColors(containerColor = LightOrange),
                elevation = CardDefaults.elevatedCardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        top = 4.dp,
                        start = 6.dp,
                        end = 6.dp,
                        bottom = 2.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        catName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF644132)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Spacer(modifier = Modifier.width(85.dp))
                        Text(
                            "$teacherCount faculty",
                            fontSize = 14.sp,
                            color = Color(0xFF644132),
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.alpha(0.6f)

                        )
                    }
                }
            }
        }
    }
}



}