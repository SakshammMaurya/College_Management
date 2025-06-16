package com.example.collegemanagement.Admin.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.BannerItemView
import com.example.collegemanagement.ItemView.NoticeItemView
import com.example.collegemanagement.Models.NoticeModel
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.BANNER
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.example.collegemanagement.Viewmodel.NoticeViewModel
import com.example.collegemanagement.ui.theme.LightGreen
import com.example.collegemanagement.ui.theme.Purple80


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ManageNotice(navController: NavController) {

    val context = LocalContext.current
    val noticeViewModel: NoticeViewModel = viewModel()

    val isUploaded by noticeViewModel.isPosted.observeAsState(false)
    val isDeleted by noticeViewModel.isDeleted.observeAsState(false)
    val noticeList by noticeViewModel.noticeList.observeAsState(null)

    noticeViewModel.getNotice()
    val isUploading by noticeViewModel.isUploading.observeAsState(false)


    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isNotice by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "Notice Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            title = ""
            link = ""
            isNotice = false  
        }
    }

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    var expandedCardId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                    text = "Manage Notice",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black
                    )
                        },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null, modifier = Modifier.size(30.dp))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isNotice = true }, containerColor = LightGreen) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black)
            }
        }
    ) { padding ->
        
        if(noticeList?.isEmpty() == true){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier=Modifier.fillMaxSize()
            ) {
                Icon(painter = painterResource(id = R.drawable.empty),
                    contentDescription =null,
                    modifier = Modifier.size(100.dp),
                    tint = Color.Gray
                    )
                Text(
                    text = "Add notice",
                    fontStyle = FontStyle.Normal,
                    color = Color.Gray
                    )
            }
        }
            Column(modifier = Modifier.padding(padding)) {

                if (isNotice) {
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    ) {
                        if (isUploading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Transparent)
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(color = LightGreen)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Uploading notice...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontStyle = FontStyle.Italic,
                                        color = Color.DarkGray
                                    )
                                }
                            }
                        }
                        else {
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                placeholder = { Text("Notice Title") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            )

                            OutlinedTextField(
                                value = link,
                                onValueChange = { link = it },
                                placeholder = { Text("Notice link") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            )

                            Image(
                                painter = if (imageUri == null) painterResource(id = R.drawable.image) else rememberAsyncImagePainter(model = imageUri),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(250.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable { launcher.launch("image/*") }
                            )

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        if (imageUri == null) {
                                            Toast.makeText(context, "Please select image", Toast.LENGTH_SHORT).show()
                                        } else if (title.isBlank()) {
                                            Toast.makeText(context, "Please provide title", Toast.LENGTH_SHORT).show()
                                        } else {
                                            noticeViewModel.saveNotice(imageUri!!, title, link)
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                ) {
                                    Text("Add Notice")
                                }

                                OutlinedButton(
                                    onClick = {
                                        imageUri = null
                                        isNotice = false
                                        title = ""
                                        link = ""
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                ) {
                                    Text("Cancel")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
                LazyColumn {
                    items(noticeList ?: emptyList()) {
                        NoticeItemView(
                            noticeModel = it,
                            delete = { notice ->
                                noticeViewModel.deleteNotice(notice)
                            }
                        )
                    }
                }
            }

    }
}


