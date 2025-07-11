package com.example.collegemanagement.Admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.BannerItemView
import com.example.collegemanagement.ItemView.NoticeItemView
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.BANNER
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.example.collegemanagement.Viewmodel.NoticeViewModel
import com.example.collegemanagement.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNotice(navController: NavController) {

    val context = LocalContext.current
    val noticeViewModel : NoticeViewModel = viewModel()

    val isUploaded by noticeViewModel.isPosted.observeAsState(false)
    val isDeleted by noticeViewModel.isDeleted.observeAsState(false)
    val noticeList by noticeViewModel.noticeList.observeAsState(null)

    noticeViewModel.getNotice()

    var imageUri by remember{
        mutableStateOf<Uri?>(  null)
    }
    var isNotice by remember{
        mutableStateOf(  false)
    }

    var title by remember{
        mutableStateOf(  "")
    }

    var link by remember{
        mutableStateOf(  "")
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect(isUploaded) {
        if(isUploaded){
            Toast.makeText(context, "Notice Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isNotice=false
        }
    }
    LaunchedEffect(isDeleted) {
        if(isDeleted){
            Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage Notice",
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
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isNotice = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription =null,
                    tint = Color.Black)
            }
        }
    ){ padding->

        Column(modifier = Modifier.padding(padding)){



            if(isNotice)
                ElevatedCard(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                ) {

                    OutlinedTextField(value = title, onValueChange = {
                        title = it
                    },
                        placeholder = { Text(text = "Notice Title")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))

                    OutlinedTextField(value = link, onValueChange = {
                        link = it
                    },
                        placeholder = { Text(text = "Notice link")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))

                    Image(painter = if(imageUri==null) painterResource(id = R.drawable.image)
                    else rememberAsyncImagePainter(model = imageUri),
                        contentDescription = NOTICE,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(250.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(onClick = {
                            if(imageUri==null) {
                                Toast.makeText(
                                    context,
                                    "Please select image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else if(title==""){
                                Toast.makeText(
                                    context,
                                    "Please provide title",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else{
                                noticeViewModel.saveNotice(imageUri!!,title,link)
                            }



                        },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)) {
                            Text(text = "Add Notice")
                        }
                        OutlinedButton(onClick = { imageUri=null
                                                   isNotice = false},
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)) {
                            Text(text = "Cancel")
                        }
                    }
                }

            LazyColumn (){
                items(noticeList?: emptyList()){
                    NoticeItemView(
                        noticeModel = it,
                        delete = { notice->
                            noticeViewModel.deleteNotice(notice)
                        }
                    )
                }
            }

        }

    }

}