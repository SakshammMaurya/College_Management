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
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.NoticeItemView
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.example.collegemanagement.Viewmodel.CollegeInfoViewModel
import com.example.collegemanagement.Viewmodel.NoticeViewModel
import com.example.collegemanagement.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCollegeInfo(navController: NavController) {
    val context = LocalContext.current
    val collegeInfoViewModel :CollegeInfoViewModel = viewModel()

    val isUploaded by collegeInfoViewModel.isPosted.observeAsState(false)
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)


    collegeInfoViewModel.getCollegeInfo()

    var imageUri by remember{
        mutableStateOf<Uri?>(  null)
    }


    var title by remember{
        mutableStateOf(  "")
    }
    var imageUrl by remember{
        mutableStateOf(  "")
    }

    var desc by remember{
        mutableStateOf(  "")
    }
    var address by remember{
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
            Toast.makeText(context, "College Info Updated", Toast.LENGTH_SHORT).show()
            imageUri = null

        }
    }
    
    LaunchedEffect(collegeInfo) {
        if(collegeInfo!=null) {
            title = collegeInfo!!.name!!
            link = collegeInfo!!.websiteLink!!
            desc = collegeInfo!!.desc!!
            address = collegeInfo!!.address!!
            imageUrl = collegeInfo!!.imageUrl!!
        }

    }


    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage College Info",
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
        }

    ){ padding->

        Column(modifier = Modifier.padding(padding)){


                ElevatedCard(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                ) {

                    OutlinedTextField(value = title, onValueChange = {
                        title = it
                    },
                        placeholder = { Text(text = "College Name..")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))
                    OutlinedTextField(value = desc, onValueChange = {
                        desc = it
                    },
                        placeholder = { Text(text = "College Description..")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))
                    OutlinedTextField(value = address, onValueChange = {
                        address = it
                    },
                        placeholder = { Text(text = "College Address..")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))

                    OutlinedTextField(value =link , onValueChange = {
                         link= it
                    },
                        placeholder = { Text(text = "Website link")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))

                    Image(painter = if(imageUrl!="") rememberAsyncImagePainter(model = imageUrl) else if(imageUri==null) painterResource(id = R.drawable.image)
                    else rememberAsyncImagePainter(model = imageUri),
                        contentDescription = NOTICE,
                        contentScale = ContentScale.Crop,
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
                          if(title=="" || desc=="" || link=="" || address==""){
                                Toast.makeText(
                                    context,
                                    "Please provide all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else  if(imageUrl!="") {
                              collegeInfoViewModel.uploadImage(imageUrl,title,address,desc,link)
                          }else  if(imageUri==null) {
                              Toast.makeText(
                                  context,
                                  "Please select image",
                                  Toast.LENGTH_SHORT
                              ).show()
                          }
                          else{
                                collegeInfoViewModel.saveImage(imageUri!!,title,address,desc,link)
                            }



                        },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)) {
                            Text(text = "Update College Info")
                        }
                    }
                }

        }

    }

}