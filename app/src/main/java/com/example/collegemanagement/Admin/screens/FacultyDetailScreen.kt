package com.example.collegemanagement.Admin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.TeacherItemView
import com.example.collegemanagement.Models.FacultyModel
import com.example.collegemanagement.Viewmodel.FacultyViewModel
import com.example.collegemanagement.ui.theme.Purple80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyDetailScreen(navController: NavController, catName: String
){


    val context = LocalContext.current
    val facultyViewModel : FacultyViewModel = viewModel()

    val facultyList by facultyViewModel.facultyList.observeAsState(null)

    facultyViewModel.getFaculty(catName)

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(text = catName,
                    fontWeight = FontWeight.Bold)
            },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple80),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()}) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft , contentDescription =null,
                            modifier = Modifier.size(30.dp))
                    }
                },

                )
        },
    ) { padding ->
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 125.dp),
            modifier=Modifier.padding(padding)) {
            items(facultyList?:emptyList()){
                TeacherItemView(facultyModel = it, delete = {facultyModel->
                    facultyViewModel.deleteFaculty(facultyModel)
                })

            }
        }
    }
}