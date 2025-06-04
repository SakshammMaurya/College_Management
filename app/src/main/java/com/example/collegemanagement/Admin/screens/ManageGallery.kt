package com.example.collegemanagement.Admin.screens

import android.app.AlertDialog
import android.hardware.lights.Light
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.GalleryItemView
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.example.collegemanagement.Viewmodel.FacultyViewModel
import com.example.collegemanagement.Viewmodel.GalleryViewModel
import com.example.collegemanagement.ui.theme.LightYellow
import com.example.collegemanagement.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageGallery(navController: NavController) {


    val context = LocalContext.current
    val galleryViewModel : GalleryViewModel = viewModel()

    val isUploaded by galleryViewModel.isPosted.observeAsState(false)
    val isDeleted by galleryViewModel.isDeleted.observeAsState(false)
    val galleryList by galleryViewModel.galleryList.observeAsState(null)

    val option = arrayListOf<String>()
    galleryViewModel.getGallery()

    var imageUri by remember{
        mutableStateOf<Uri?>(  null)
    }
    var isCategory by remember{
        mutableStateOf(  false)
    }
    var mExpanded by remember{
        mutableStateOf(  false)
    }
    var isImage by remember{
        mutableStateOf(  false)
    }


    var category by remember{
        mutableStateOf(  "")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect(isUploaded) {
        if(isUploaded){
            Toast.makeText(context, "Data Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory=false
            category=""

        }
    }
    LaunchedEffect(isDeleted) {
        if(isDeleted){
            Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Manage Gallery",
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
            FloatingActionButton(onClick = { showDialog = true }, containerColor = LightYellow) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ){ padding->

            LazyColumn (modifier = Modifier.padding(padding)){
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Action") },
            confirmButton = {
                Column {
                    Button(onClick = {
                        isCategory = true
                        isImage = false
                        showDialog = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Add Category")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        isImage = true
                        isCategory = false
                        showDialog = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Add Image")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = { showDialog = false }, modifier = Modifier.fillMaxWidth()) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
    if(isCategory) {
        ElevatedCard(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = category, onValueChange = {
                    category = it
                },
                placeholder = { Text(text = "Category...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )

            Image(painter = if (imageUri == null) painterResource(id = R.drawable.image)
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
            ) {
                Button(
                    onClick = {
                        if (category == "" || imageUri == null) {
                            Toast.makeText(
                                context,
                                "Please provide all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            galleryViewModel.saveGalleryImage(imageUri!!, category, true)
                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Text(text = "Add Category")
                }
                OutlinedButton(
                    onClick = {
                        imageUri = null
                        isCategory = false
                        isImage = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
    if(isImage) {
        ElevatedCard(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(modifier = Modifier.height(5.dp))
                Image(painter = if (imageUri == null) painterResource(id = R.drawable.image)
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

                Spacer(modifier = Modifier.height(5.dp))

                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {


                    OutlinedTextField(
                        value = category, onValueChange = {
                            category = it
                        },
                        readOnly = true,
                        placeholder = { Text(text = "Select Gallery...") },
                        label = { Text(text = "Gallery Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = mExpanded)
                        }
                    )

                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false }) {

                        if (galleryList != null && galleryList!!.isNotEmpty()) {
                            option.clear()
                            for (data in galleryList!!) {
                                option.add(data.category!!)
                            }
                        }

                        option.forEach { selectedOption ->
                            DropdownMenuItem(
                                text = { Text(text = selectedOption) },
                                onClick = {
                                    category = selectedOption
                                    mExpanded = false

                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                        }
                    }
                    Spacer(modifier = Modifier
                        .matchParentSize()
                        .padding(10.dp)
                        .clickable {
                            mExpanded = !mExpanded
                        })
                }


                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (imageUri == null) {
                                Toast.makeText(
                                    context,
                                    "Please select image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (category == "") {
                                Toast.makeText(
                                    context,
                                    "Please provide category",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                galleryViewModel.saveGalleryImage(
                                    imageUri!!,
                                    category,
                                    false
                                )
                            }
                            isImage = false
                            isCategory = false

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Add Image")
                    }
                    OutlinedButton(
                        onClick = {
                            imageUri = null
                            isCategory = false
                            isImage = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }

        }
    }


}