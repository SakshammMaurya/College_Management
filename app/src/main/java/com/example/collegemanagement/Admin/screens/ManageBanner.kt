package com.example.collegemanagement.Admin.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.BannerItemView
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Utils.Constants.BANNER
import com.example.collegemanagement.Viewmodel.BannerViewModel
import com.example.collegemanagement.Viewmodel.NoticeViewModel
import com.example.collegemanagement.Widget.LoadingDialog
import com.example.collegemanagement.ui.theme.Purple80

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBanner(navController: NavController) {

    val context = LocalContext.current
    val bannerViewModel : BannerViewModel = viewModel()

    val isUploaded by bannerViewModel.isPosted.observeAsState(false)
    val isDeleted by bannerViewModel.isDeleted.observeAsState(false)
    val bannerList by bannerViewModel.bannerList.observeAsState(null)

    bannerViewModel.getBanner()

    val showLoader = remember{
        mutableStateOf(false)
    }
    if(showLoader.value){
        LoadingDialog(onDismissRequest = { showLoader.value = false })
    }

    var imageUri by remember{
        mutableStateOf<Uri?>(  null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect(isUploaded) {
        showLoader.value=false
       if(isUploaded){
           Toast.makeText(context, "Image Uploaded",Toast.LENGTH_SHORT).show()
           imageUri = null
       }
    }
    LaunchedEffect(isDeleted) {
        if(isDeleted){
            Toast.makeText(context, "Image Deleted",Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage Banner",
                    fontWeight = FontWeight.Bold)
            },
            colors =TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple80),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()}) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft , contentDescription =null,
                            modifier = Modifier.size(30.dp))
                    }
                },

                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                launcher.launch("image/*")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription =null,
                    tint = Color.Black)
            }
        }
    ){ padding->

        Column(modifier = Modifier.padding(padding)){

            if(imageUri !=null)
            ElevatedCard(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
                ) {

                Image(painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = BANNER,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(600.dp)
                        .align(Alignment.CenterHorizontally)
                        )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(onClick = {
                        showLoader.value = true
                        bannerViewModel.saveImage(imageUri!!)
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)) {
                        Text(text = "Add Image")
                    }
                    OutlinedButton(onClick = { imageUri=null },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)) {
                        Text(text = "Cancel")
                    }
                }
            }

            LazyColumn (){
                items(bannerList?: emptyList()){
                    BannerItemView(
                        bannerModel = it,
                        delete = { banner->
                            bannerViewModel.deleteBanner(banner)
                        }
                    )
                }
            }

        }

    }

}



//@Preview(showSystemUi = true)
@Composable
fun ManageBannerPreview(modifier: Modifier = Modifier) {
    ManageBanner(rememberNavController())
}