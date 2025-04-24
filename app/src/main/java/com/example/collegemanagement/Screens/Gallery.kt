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
import com.example.collegemanagement.ItemView.GalleryItemView
import com.example.collegemanagement.Viewmodel.GalleryViewModel

@Composable
fun Gallery(navController: NavController) {


    val galleryViewModel : GalleryViewModel = viewModel()
    val galleryList by galleryViewModel.galleryList.observeAsState(null)
    galleryViewModel.getGallery()



    LazyColumn (){
        items(galleryList?: emptyList()){
            GalleryItemView(
                it,
                delete = { docId->
                    galleryViewModel.deleteGallery(docId)

                }, deleteImage = {cat,imageUrl->
                    galleryViewModel.deleteImage(cat,imageUrl)
                }
            )
        }
    }
}