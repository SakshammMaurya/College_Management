package com.example.collegemanagement.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.collegemanagement.ItemView.GalleryItemView
import com.example.collegemanagement.Viewmodel.GalleryViewModel

@Composable
fun Gallery(navController: NavController) {


    val galleryViewModel: GalleryViewModel = viewModel()
    val galleryList by galleryViewModel.galleryList.observeAsState(null)
    galleryViewModel.getGallery()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Gallery",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(8.dp))
    LazyColumn() {
        items(galleryList ?: emptyList()) {
            GalleryItemView(
                it,
                delete = { docId ->
                    galleryViewModel.deleteGallery(docId)

                }, deleteImage = { cat, imageUrl ->
                    galleryViewModel.deleteImage(cat, imageUrl)
                }
            )
        }
    }
}
}
