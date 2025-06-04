package com.example.collegemanagement.Admin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.TeacherItemView
import com.example.collegemanagement.Models.FacultyModel
import com.example.collegemanagement.Utils.Constants.isAdmin
import com.example.collegemanagement.Viewmodel.FacultyViewModel
import com.example.collegemanagement.ui.theme.Purple80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyDetailScreen(navController: NavController, catName: String
){


    val context = LocalContext.current
    val facultyViewModel : FacultyViewModel = viewModel()

    val facultyList by facultyViewModel.facultyList.observeAsState(null)
    var showDeleteDialog by remember { mutableStateOf(false) }


    facultyViewModel.getFaculty(catName)

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = catName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {

                    if(isAdmin)
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Category"
                        )
                    }
                }
            )

        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()

        ) {
            items(facultyList ?: emptyList()) { faculty ->
                TeacherItemView(
                    facultyModel = faculty,
                    delete = { facultyModel ->
                        facultyViewModel.deleteFaculty(facultyModel)
                    }
                )
//                Spacer(modifier = Modifier.height(12.dp)) // Optional spacing between items
            }
        }
//        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 125.dp),
//            modifier=Modifier.padding(padding)) {
//            items(facultyList?:emptyList()){
//                TeacherItemView(facultyModel = it, delete = {facultyModel->
//                    facultyViewModel.deleteFaculty(facultyModel)
//                })
//
//            }
//        }

    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Category") },
            text = { Text("Are you sure you want to delete '$catName' and all its teachers?") },
            confirmButton = {
                TextButton(onClick = {
                    facultyViewModel.deleteCategoryWithTeachers(catName) {
                        showDeleteDialog = false
                        navController.popBackStack() // go back to category list
                    }
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

}