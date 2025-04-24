package com.example.collegemanagement.Screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.collegemanagement.ItemView.FacultyItemView
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.Viewmodel.FacultyViewModel

@Composable
fun Faculty(navController: NavController) {

    val facultyViewModel : FacultyViewModel = viewModel()

    val categoryList by facultyViewModel.categoryList.observeAsState(null)

    facultyViewModel.getCategory()


    LazyColumn (){
        items(categoryList?: emptyList()){
            FacultyItemView(
                catName = it,
                delete = { docId->
                    facultyViewModel.deleteCategory(docId)

                }, onClick = {categoryName->
                    val routes = Routes.FacultyDetailScreen.route.replace("{catName}",categoryName)

                    navController.navigate(routes)
                }
            )
        }
    }


}